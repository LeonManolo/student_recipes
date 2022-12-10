package de.hsflensburg.recipe_backend.ingredients.dto

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient

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

fun IngredientResponseDto.addIngredient(ingredient: Ingredient, amount: Double) {
    this.calories += ingredient.calories / 100 * amount
    this.protein += ingredient.protein / 100 * amount
    this.fat += ingredient.fat / 100 * amount
    this.carbohydrates += ingredient.carbohydrates / 100 * amount
}
