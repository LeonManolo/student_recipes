package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.model.User
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User) {
        userRepository.save(user)
    }

    fun getUser(id: Long) : User? {
        return userRepository.findById(id).orElse(null)
    }

    fun getUserByEmail(email: String) : User? {
        return userRepository.findByEmail(email)
    }
}