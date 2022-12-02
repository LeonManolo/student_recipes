package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    //fun findByCalculationGreaterThanEqual(num: Int): Iterable<Ingredient>
    fun findByIdAndEmail(id: Long, email: String): User?

    fun findByEmail(email: String): User?
}