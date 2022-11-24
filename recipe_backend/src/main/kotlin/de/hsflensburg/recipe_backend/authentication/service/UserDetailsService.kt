package de.hsflensburg.recipe_backend.authentication.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException


interface UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    fun loadUserByUsername(username: String): UserDetails?
}
