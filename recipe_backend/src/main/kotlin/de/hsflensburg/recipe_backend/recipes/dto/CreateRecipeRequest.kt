package de.hsflensburg.recipe_backend.recipes.dto

import de.hsflensburg.recipe_backend.recipes.Recipe
import javax.validation.constraints.NotBlank

data class CreateRecipeRequest(
    @field:NotBlank
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val tags: List<String>,
    val image: String
)


fun CreateRecipeRequest.toRecipe() {
}
