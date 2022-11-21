package de.hsflensburg.recipe_backend.authentication.dto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.beans.Encoder

class RegisterDto(
    //val username: String,
    val password: String,
    val email: String
) {
}
