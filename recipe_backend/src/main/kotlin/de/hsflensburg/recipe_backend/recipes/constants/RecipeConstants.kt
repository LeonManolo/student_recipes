package de.hsflensburg.recipe_backend.recipes.constants

class RecipeConstants {
    companion object {
        const val PROTEIN_COLUMN = "protein"
        const val PROTEIN_FORMULA = RecipeConstants.PRE_SQL + PROTEIN_COLUMN + RecipeConstants.POST_SQL
        const val CALORIES_COLUMN = "calories"
        const val CALORIES_FORMULA = RecipeConstants.PRE_SQL + CALORIES_COLUMN + RecipeConstants.POST_SQL
        private const val PRE_SQL = "(SELECT SUM(ingredient_info.amount / 100 * ingredient."
        private const val POST_SQL =
            ") FROM recipe INNER JOIN recipe_step ON recipe.id = recipe_step.recipe_id INNER JOIN ingredient_info ON recipe_step.id = ingredient_info.recipe_step_id INNER JOIN ingredient ON ingredient_info.ingredient_id = ingredient.id WHERE recipe.id = id)"
    }

}