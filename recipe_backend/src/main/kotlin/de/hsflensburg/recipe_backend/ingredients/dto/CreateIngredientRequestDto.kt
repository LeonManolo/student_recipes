package de.hsflensburg.recipe_backend.ingredients.dto

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import org.hibernate.validator.constraints.Range
import javax.validation.Valid
import javax.validation.constraints.NotBlank

//TODO: implementieren
data class CreateIngredientRequestDto (
    @Valid
    val locale: LanguageSelection = LanguageSelection.German, //TODO: LanguageSelection

    @field:NotBlank
    val title: String,

    @field:Range(min = 0)
    val calories: Double = .0,

    @field:Range(min = 0, max = 100)
    val protein: Double = .0,

    @field:Range(min = 0, max = 100)
    val carbohydrates: Double = .0,

    @field:Range(min = 0, max = 100)
    val fat: Double = .0,
)

fun CreateIngredientRequestDto.toIngredient() = Ingredient(
    locale = locale,
    title = title,
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fat = fat,
)