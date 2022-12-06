package de.hsflensburg.recipe_backend.associations;

import de.hsflensburg.recipe_backend.associations.entity.Rating
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface RatingRepository : JpaRepository<Rating, Long> {


    fun deleteByUserAndRecipe(user: User, recipe: Recipe): Long

}