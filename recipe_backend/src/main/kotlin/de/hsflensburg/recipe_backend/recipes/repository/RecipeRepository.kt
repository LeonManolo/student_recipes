package de.hsflensburg.recipe_backend.recipes.repository

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, Long> {
    fun findByTotalCaloriesGreaterThanEqual(totalCalories: Double): Iterable<Recipe>


    fun findByFavoritedBy_User_Id(id: Long): List<Recipe>

}