package de.hsflensburg.recipe_backend.ingredients

import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository: JpaRepository<Ingredient, Long> {
    fun findByCalculationGreaterThanEqual(num: Int): Iterable<Ingredient>
}