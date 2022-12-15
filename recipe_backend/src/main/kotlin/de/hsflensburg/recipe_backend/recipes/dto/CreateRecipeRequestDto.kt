package de.hsflensburg.recipe_backend.recipes.dto

import de.hsflensburg.recipe_backend.categories.entity.Category
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Positive

data class CreateRecipeRequestDto(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val description: String,

    @field:Min(1)
    val servings: Int,


    @field:NotEmpty
    @field:Valid
    val steps: List<RecipeStepDto>,
    //val tags: List<String> = emptyList(),
    var image: String? = null,

    val categories: List<Long> = emptyList(),

    val price: Double = 0.0,
    val cookTime: Int = 0
)


data class RecipeStepDto(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val description: String,
    @field:Valid
    val ingredients: List<IngredientInfoDto> = emptyList(),
    val imageUrl: String? = null,
)

data class IngredientInfoDto(
    val ingredientId: Long,
    @field:Positive
    val amount: Double,
    @field:NotBlank
    val unit: String,
)


fun CreateRecipeRequestDto.toRecipe() {
}
