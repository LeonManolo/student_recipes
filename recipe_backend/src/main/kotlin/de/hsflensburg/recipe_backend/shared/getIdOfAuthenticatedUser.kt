package de.hsflensburg.recipe_backend.shared

import de.hsflensburg.recipe_backend.authentication.entity.UserDetailsImpl
import org.springframework.security.core.context.SecurityContextHolder

fun getIdOfAuthenticatedUser(): Long {
    val user = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    return user.getId()
}