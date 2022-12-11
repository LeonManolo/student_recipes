package de.hsflensburg.recipe_backend.recipes.extension

import de.hsflensburg.recipe_backend.ingredients.dto.IngredientResponseDto
import de.hsflensburg.recipe_backend.ingredients.dto.addIngredient
import de.hsflensburg.recipe_backend.ingredients.dto.toIngredientResponseDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe

fun Recipe.sumIngredients(): MutableList<IngredientResponseDto> {
    val ingredientMap = mutableMapOf<Long, IngredientResponseDto>()
    steps.forEach { step ->
        step.ingredients.forEach { ingredientInfo ->
            val ingredient = ingredientInfo.ingredient!!
            if(ingredientMap.containsKey(ingredient.id!!)) {
                ingredientMap[ingredient.id]!!.addIngredient(ingredient, ingredientInfo.amount)
            } else {
                ingredientMap[ingredient.id!!] =
                    ingredient.toIngredientResponseDto(ingredientInfo.amount, ingredientInfo.unit)
            }
        }
    }
    return ingredientMap.values.toMutableList()
}