package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.dto.UpdateUserDto
import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * The UserService class represents a spring service class that provides
 * methods for retrieving Users from the UserRepository
 */
@Service
class UserService(private val userRepository: UserRepository,
                  private val encoder: PasswordEncoder,) {

    fun createUser(user: User) {
        userRepository.save(user)
    }

    fun getUser(id: Long) : User {
        return userRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id $id not found")
        }
    }

    fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    fun updateUser(id: Long, user: UpdateUserDto) {
        val userToUpdate = getUser(id)
        user.firstName?.let { userToUpdate.firstName = it }
        user.lastName?.let { userToUpdate.lastName = it }
        user.email?.let { userToUpdate.email = it }
        user.password?.let { userToUpdate.password = encoder.encode(it) }
        user.imageUrl?.let { userToUpdate.imageUrl = it }
        userRepository.save(userToUpdate)
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with email $email not found")
    }
}