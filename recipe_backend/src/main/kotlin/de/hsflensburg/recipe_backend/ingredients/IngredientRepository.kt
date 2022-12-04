package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.data.jpa.repository.JpaRepository

interface IngredientRepository: JpaRepository<Ingredient, Long> {
    fun findByCalculationGreaterThanEqual(num: Int): Iterable<Ingredient>

    //TODO: und dann vielleicht mit @Valid schauen das array nicht leer ist
    //fun findByIdAndLocale(id: Long, locale: String): Ingredient?

}