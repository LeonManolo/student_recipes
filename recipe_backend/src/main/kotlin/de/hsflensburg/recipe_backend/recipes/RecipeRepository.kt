package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.recipes.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, Long> {
}