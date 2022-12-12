package de.hsflensburg.recipe_backend.ingredients.dto

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import org.hibernate.validator.constraints.Range
import javax.validation.Valid
import javax.validation.constraints.NotBlank

/**
 * Data class representing a request to create a new ingredient.
 *
 * @param locale The locale for the ingredient.
 * @param title The title of the ingredient.
 * @param calories The number of calories in the ingredient.
 * @param protein The amount of protein in the ingredient, per 100g or 100ml.
 * @param carbohydrates The amount of carbohydrates in the ingredient, per 100g or 100ml.
 * @param fat The amount of fat in the ingredient, per 100g or 100ml.
 */
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


/**
 * Converts this [CreateIngredientRequestDto] object to an [Ingredient] entity/object.
 *
 * @return The [Ingredient] entity/object.
 */
fun CreateIngredientRequestDto.toIngredient() = Ingredient(
    locale = locale,
    title = title,
    calories = calories,
    protein = protein,
    carbohydrates = carbohydrates,
    fat = fat,
)