package de.hsflensburg.recipe_backend.ingredients.dto

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient

/**
 * Data class representing a response containing information about an ingredient.
 *
 * @property id The ID of the ingredient.
 * @property title The title of the ingredient.
 * @property calories The number of calories in the ingredient.
 * @property protein The amount of protein in the ingredient, per 100g or 100ml.
 * @property fat The amount of fat in the ingredient, per 100g or 100ml.
 * @property carbohydrates The amount of carbohydrates in the ingredient, per 100g or 100ml.
 * @property imageUrl The URL of an image for the ingredient.
 * @property unit The unit for the ingredient (g or ml).
 */
data class IngredientResponseDto(
    val id: Long,
    val title: String,
    var calories: Double,
    var protein: Double,
    var fat: Double,
    var carbohydrates: Double,
    val imageUrl: String,
    val unit: String,
)

/**
 * Converts the given [Ingredient] object to an [IngredientResponseDto] object.
 *
 * @param amount The amount of the ingredient, in grams or milliliters.
 * @param unit The unit for the ingredient.
 *
 * @return The [IngredientResponseDto] object.
 */
fun Ingredient.toIngredientResponseDto(amount: Double = 100.0, unit: String) = IngredientResponseDto(
    id = this.id!!,
    title = this.title,
    calories = this.calories / 100 * amount,
    protein = this.protein / 100 * amount,
    fat = this.fat / 100 * amount,
    carbohydrates = this.carbohydrates / 100 * amount,
    imageUrl = "",
    unit = unit,
)

/**
 * Adds the given [ingredient] and its [amount] to this ingredient response object,
 * updating the total calories, protein, fat, and carbohydrates accordingly.
 *
 * @param ingredient The ingredient to add.
 * @param amount The amount of the ingredient to add, in grams.
 */
fun IngredientResponseDto.addIngredient(ingredient: Ingredient, amount: Double) {
    this.calories += ingredient.calories / 100 * amount
    this.protein += ingredient.protein / 100 * amount
    this.fat += ingredient.fat / 100 * amount
    this.carbohydrates += ingredient.carbohydrates / 100 * amount
}
