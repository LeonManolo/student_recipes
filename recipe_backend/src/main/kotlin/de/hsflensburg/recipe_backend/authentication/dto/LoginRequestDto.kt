package de.hsflensburg.recipe_backend.authentication.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class LoginRequestDto(
    @field:NotBlank
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String
)
