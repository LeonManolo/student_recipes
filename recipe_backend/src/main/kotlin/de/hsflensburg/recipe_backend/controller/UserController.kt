package de.hsflensburg.recipe_backend.controller

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.model.User
import de.hsflensburg.recipe_backend.repo.UserRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/users")
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