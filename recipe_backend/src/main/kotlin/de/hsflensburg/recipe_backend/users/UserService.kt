package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(user: User) {
        userRepository.save(user)
    }

    fun getUser(id: Long) : User {
        return userRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id $id not found")
        }
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with email $email not found")
    }
}