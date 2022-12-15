package de.hsflensburg.recipe_backend.users.dto

/**
 * A DTO that represents a user update request.
 */
data class UpdateUserDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val password: String? = null,
    var imageUrl: String? = null,
)
