package de.hsflensburg.recipe_backend.shared

import com.fasterxml.jackson.annotation.JsonProperty

enum class LanguageSelection {
    @JsonProperty("de")
    German,
    @JsonProperty("en")
    English,
}