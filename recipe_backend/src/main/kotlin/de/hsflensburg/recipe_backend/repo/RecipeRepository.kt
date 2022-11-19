package de.hsflensburg.recipe_backend.repo

import de.hsflensburg.recipe_backend.model.Recipe
import org.springframework.data.jpa.repository.JpaRepository

interface RecipeRepository: JpaRepository<Recipe, Long> {
}