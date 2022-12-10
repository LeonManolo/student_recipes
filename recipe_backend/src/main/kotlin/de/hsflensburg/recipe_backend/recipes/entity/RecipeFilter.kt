package de.hsflensburg.recipe_backend.recipes.entity

enum class RecipeFilter(s: String) {
    NEWEST("newest"),
    MOST_VIEWED("most_viewed"),
    BEST_RATED("best_rated"),
    MOST_FAVORITES("most_favorites"),
    FAST_TO_COOK("fast_to_cook"),
    CHEAP("cheap"),
}