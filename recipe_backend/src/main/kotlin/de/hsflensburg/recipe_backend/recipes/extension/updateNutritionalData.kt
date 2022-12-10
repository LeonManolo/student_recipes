package de.hsflensburg.recipe_backend.recipes.extension

import de.hsflensburg.recipe_backend.recipes.entity.Recipe

/**
 * Updates nutritional data of the recipe
 */
internal fun Recipe.updateNutritionalData() {
    var totalCalories = 0.0
    var totalProtein = 0.0
    var totalFat = 0.0
    var totalCarbohydrates = 0.0

    for (step in steps) {
        for (ingredientInfo in step.ingredients) {
            val amount = ingredientInfo.amount / 100.0
            totalCalories += amount * ingredientInfo.ingredient!!.calories
            totalProtein += amount * ingredientInfo.ingredient!!.protein
            totalFat += amount * ingredientInfo.ingredient!!.fat
            totalCarbohydrates += amount * ingredientInfo.ingredient!!.carbohydrates
        }
    }

    this.totalCalories = totalCalories
    this.totalProtein = totalProtein
    this.totalFat = totalFat
    this.totalCarbohydrates = totalCarbohydrates
}