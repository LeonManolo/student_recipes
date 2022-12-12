package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.recipes.repository.FavoriteRepository
import de.hsflensburg.recipe_backend.recipes.repository.RatingRepository
import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.entity.RecipeFilter
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import de.hsflensburg.recipe_backend.recipes.repository.RecipeRepository
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
@Transactional // muss rein damit @Formula funktioniert
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val userRepository: UserRepository,
) {
    fun createRecipe(recipe: CreateRecipeRequestDto): Recipe {
        val user = userRepository.findById(recipe.authorId).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id ${recipe.authorId} not found")
        }

        val recipeToSave = Recipe(
            title = recipe.title,
            description = recipe.description,
            servings = recipe.servings,
            author = user,
        )
        val savedRecipe = recipeRepository.save(recipeToSave)

        recipe.steps.mapIndexed { index, step ->
            val recipeStep = RecipeStep( // wieso kriegt recipeStep die recipe_id automatisch?
                stepNumber = index,
                title = step.title,
                description = step.description,
                recipe = savedRecipe,
                imageUrl = step.imageUrl
            )
            savedRecipe.steps.add(recipeStep)
            recipeRepository.save(savedRecipe)
            savedRecipe.steps.last().ingredients = step.ingredients.map { ingredient ->
                IngredientInfo(
                    recipeStep = savedRecipe.steps.last(),
                    amount = ingredient.amount,
                    unit = ingredient.unit,
                    ingredient = ingredientRepository.findById(ingredient.ingredientId).get()
                )
            }.toMutableSet()

        }
        return recipeRepository.save(savedRecipe)

    }

    fun getRecipe(id: Long): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id $id not found")
        }
        recipe.views += 1
        return recipe
    }

    fun getRecipes(filter: RecipeFilter?): List<Recipe> {
        return when (filter) {
            RecipeFilter.NEWEST -> recipeRepository.findAllByOrderByCreatedAtDesc()
            RecipeFilter.MOST_VIEWED -> recipeRepository.findAllByOrderByViewsDesc()
            RecipeFilter.FAST_TO_COOK -> recipeRepository.findAllByOrderByCookTimeDesc() //recipeRepository.findAllByOrderByRatingDesc()
            RecipeFilter.CHEAP -> recipeRepository.findAllByOrderByPriceDesc() //recipeRepository.findAllByOrderByFavoritesDesc()
            else -> {
                recipeRepository.findAll()
            }
        }
    }

    fun deleteRecipe(id: Long) {
        recipeRepository.deleteById(id)
    }

    fun getFavoriteRecipesForUser(userId: Long): List<Recipe> {
        return recipeRepository.findByFavoritedBy_User_Id(userId)
    }

    fun updateRecipe(id: Long, recipeDTO: CreateRecipeRequestDto): Recipe {
        val recipe = getRecipe(id)

        recipe.steps.clear()

        recipeDTO.steps.mapIndexed { index, step ->
            val recipeStep = RecipeStep(
                stepNumber = index,
                title = step.title,
                description = step.description,
                recipe = recipe,
                imageUrl = step.imageUrl
            )
            recipeStep.ingredients = step.ingredients.map { ingredient ->
                IngredientInfo(
                    recipeStep = recipeStep,
                    amount = ingredient.amount,
                    unit = ingredient.unit,
                    ingredient = ingredientRepository.findById(ingredient.ingredientId).get()
                )
            }.toMutableSet()
            recipe.steps.add(recipeStep)
        }

        recipe.title = recipeDTO.title
        recipe.description = recipeDTO.description
        recipe.servings = recipeDTO.servings
        recipe.author = userRepository.findById(recipeDTO.authorId).get()

        return recipe
    }
}