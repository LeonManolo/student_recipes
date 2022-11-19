package de.hsflensburg.recipe_backend.authentication.dto

data class LoginDto(
    val email: String,
    val password: String
)
