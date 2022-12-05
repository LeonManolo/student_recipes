package de.hsflensburg.recipe_backend.recipes;

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.entity.RecipeLikes
import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeLikesRepository : JpaRepository<RecipeLikes, Long> {


    fun findByUser_Id(id: Long): List<RecipeLikes>


    fun deleteByUser_IdAndRecipe_Id(user_id: Long, recipe_id: Long): Long

}