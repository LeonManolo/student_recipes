package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RecipeService(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun createRecipe(recipe: CreateRecipeRequestDto) {
        val recipeToSave = Recipe(
            title = recipe.title,
            description = recipe.description,
            servings = recipe.servings,
            author = userRepository.findById(recipe.authorId).get(),
        )
        val savedRecipe = recipeRepository.save(recipeToSave)
        println(recipeToSave.id)
        println(savedRecipe.id)
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

        }.toMutableSet()
        recipeRepository.save(savedRecipe)

    }

    fun getRecipe(id: Long): Recipe? {
        return recipeRepository.findById(id).orElse(null)
    }

    fun getRecipes(): List<Recipe> {
        return recipeRepository.findAll()
    }

    fun deleteRecipe(id: Long) {
        recipeRepository.deleteById(id)
    }
}