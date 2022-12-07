package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    //fun findByCalculationGreaterThanEqual(num: Int): Iterable<Ingredient>
    fun findByIdAndEmail(id: Long, email: String): User?

    fun findByEmail(email: String): User?

    // return user object with usable list of recipes.
    // needed when fetchtype is lazy.
    // when only the user is wanted but not his recipes the normal findbyId function should be used
    @Query("SELECT u FROM User u JOIN FETCH u.recipes WHERE u.id = :id")
    fun findByIdWithRecipes(@Param("id") id: Long?): User?
}