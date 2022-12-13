package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * A RestController that handles requests for the User resource
 *
 * @property userRepository The UserRepository instance used to handle User-related tasks.
 */
@RestController
@RequestMapping("/api/users")
class UserController(val userRepository: UserRepository) {
    @PostMapping
    fun createUser(@RequestBody user: User) {
        userRepository.save(user)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id:Long) : Optional<User> {
        return userRepository.findById(id)
    }
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long){
        userRepository.deleteById(id)
    }
}