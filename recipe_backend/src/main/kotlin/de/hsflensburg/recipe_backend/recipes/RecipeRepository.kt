package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, Long> {
    fun findByTotalCaloriesGreaterThanEqual(totalCalories: Double): Iterable<Recipe>


    fun findByRecipeLikes_User_Id(id: Long): List<Recipe>



}