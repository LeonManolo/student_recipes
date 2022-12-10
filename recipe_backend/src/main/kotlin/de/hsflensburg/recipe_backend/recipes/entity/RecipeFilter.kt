package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonProperty

enum class RecipeFilter {
    @field:JsonProperty("newest")
    NEWEST,
    @field:JsonProperty("most_viewed")
    MOST_VIEWED,
    @field:JsonProperty("best_rated")
    BEST_RATED,
    @field:JsonProperty("most_favorites")
    MOST_FAVORITES,
    @field:JsonProperty("fast_to_cook")
    FAST_TO_COOK,
    @field:JsonProperty("cheap")
    CHEAP,
}