package de.hsflensburg.recipe_backend.associations;

import de.hsflensburg.recipe_backend.associations.entity.Favorite
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteRepository : JpaRepository<Favorite, Long> {

    fun findByUser_Id(id: Long): List<Favorite>

    fun deleteByUser_IdAndRecipe_Id(user_id: Long, recipe_id: Long): Long


    fun deleteByUserAndRecipe(user: User, recipe: Recipe): Long

}