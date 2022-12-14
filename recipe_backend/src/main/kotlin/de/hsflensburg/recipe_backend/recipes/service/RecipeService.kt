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
import de.hsflensburg.recipe_backend.shared.getIdOfAuthenticatedUser
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

/**
 * Service class for managing [Recipe] entities
 * @property Service Marks this class as a Spring service bean.
 * @property Transactional Specifies that methods in this class will be executed within a database transaction.
 * @property RecipeRepository Repository for accessing and managing [Recipe] entities.
 * @property IngredientRepository Repository for accessing and managing [Ingredient] entities.
 * @property UserRepository Repository for accessing and managing [User] entities.
 * @constructor Creates a new [RecipeService] instance with the specified repositories.
 */
@Service
@Transactional // muss rein damit @Formula funktioniert
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val userRepository: UserRepository,
) {
    /**
     * Creates a new [Recipe] with the information provided in the [CreateRecipeRequestDto].
     * @param recipe The recipe information to be used for creating the new recipe.
     * @return The newly created [Recipe].
     * @throws ResponseStatusException with a status of [HttpStatus.NOT_FOUND] if the user with the specified id in the [CreateRecipeRequestDto] does not exist.
     */
    fun createRecipe(recipe: CreateRecipeRequestDto, userId: Long): Recipe {
        val user = userRepository.findById(userId).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id ${userId} not found")
        }

        val recipeToSave = Recipe(
            title = recipe.title,
            description = recipe.description,
            servings = recipe.servings,
            author = user,
        )
        val savedRecipe = recipeRepository.save(recipeToSave)

        recipe.steps.mapIndexed { index, step ->
            val recipeStep = RecipeStep(
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

    fun getRecipe(id: Long, increment: Boolean = false): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with id $id not found")
        }
        if (increment) recipe.views += 1
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

    fun getRecipesForCategory(filter: RecipeFilter?, id: Long): List<Recipe> {
        return when (filter){
            RecipeFilter.NEWEST -> recipeRepository.findByCategories_IdOrderByCreatedAtDesc(id)
            RecipeFilter.MOST_VIEWED -> recipeRepository.findByCategories_IdOrderByViewsDesc(id)
            RecipeFilter.BEST_RATED -> TODO()
            RecipeFilter.MOST_FAVORITES -> TODO()
            RecipeFilter.FAST_TO_COOK -> recipeRepository.findByCategories_IdOrderByCookTimeDesc(id)
            RecipeFilter.CHEAP -> recipeRepository.findByCategories_IdOrderByPriceDesc(id)
            else -> {
                recipeRepository.findByCategories_Id(id)
            }
        }
    }

    fun deleteRecipe(id: Long, userId: Long) {
        val recipe = getRecipe((id))
        if (recipe.author.id == userId){
            recipeRepository.deleteById(id)
        } else throw ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author")

    }

    fun getFavoriteRecipesForUser(userId: Long): List<Recipe> {
        return recipeRepository.findByFavoritedBy_User_Id(userId)
    }

    fun updateRecipe(id: Long, recipeDTO: CreateRecipeRequestDto, userId: Long): Recipe {
        val recipe = getRecipe(id)
        if (recipe.author.id == userId) {
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

            return recipe
        }else throw ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author")
    }
}