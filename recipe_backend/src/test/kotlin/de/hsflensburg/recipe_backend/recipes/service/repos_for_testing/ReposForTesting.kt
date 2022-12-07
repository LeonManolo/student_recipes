package de.hsflensburg.recipe_backend.recipes.service.repos_for_testing

import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import org.springframework.data.jpa.repository.JpaRepository

internal interface RecipeStepRepository : JpaRepository<RecipeStep, Long> {
    fun findByRecipeId(recipeId: Long): List<RecipeStep>
}

internal interface IngredientInfoRepository : JpaRepository<IngredientInfo, Long> {
}