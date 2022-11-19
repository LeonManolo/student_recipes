package de.hsflensburg.recipe_backend.repo

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {
        //fun findByCalculationGreaterThanEqual(num: Int): Iterable<Ingredient>
        fun findByIdAndEmail(id: Long, email: String): User?
}