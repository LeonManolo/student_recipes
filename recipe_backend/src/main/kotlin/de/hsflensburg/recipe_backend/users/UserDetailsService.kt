package de.hsflensburg.recipe_backend.users

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException


interface UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    fun loadUserByUsername(username: String): UserDetails?
}
