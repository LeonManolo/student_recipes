package de.hsflensburg.recipe_backend.recipes

import org.springframework.stereotype.Service

@Service
class RecipeService(private val recipeRepository: RecipeRepository) {
    fun createRecipe(recipe: Recipe) {
        recipeRepository.save(recipe)
    }

    fun getRecipe(id: Long) : Recipe? {
        return recipeRepository.findById(id).orElse(null)
    }
}