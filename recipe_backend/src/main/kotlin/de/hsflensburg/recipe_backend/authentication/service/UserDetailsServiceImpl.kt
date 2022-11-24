package de.hsflensburg.recipe_backend.authentication.service

import de.hsflensburg.recipe_backend.authentication.entity.UserDetailsImpl
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) :
    UserDetailsService {


    override fun loadUserByUsername(email: String): UserDetails? {
        println("geht er hier rein?")
        val user = userRepository.findByEmail(email)
        if (user != null) {
            return UserDetailsImpl(user.id!!, user.email, user.password) // TODO: unsafe
        } else {
            throw UsernameNotFoundException("User not found $email")
        }
    }
}
