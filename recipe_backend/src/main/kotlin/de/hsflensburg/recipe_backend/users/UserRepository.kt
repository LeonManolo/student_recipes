package de.hsflensburg.recipe_backend.users

import de.hsflensburg.recipe_backend.users.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
/**
 * Repository interface for [User] entities
 *
 * @property JpaRepository The base interface for accessing data in a relational database.
 * @property User The user class being managed by this repository.
 * @property Long The type of the id property of the [User] class.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {

    /**
     * Finds a [User] by their id and email address.
     *
     * @param id The id of the user to find.
     * @param email The email address of the user to find.
     * @return The user with the specified id and email address, or null if no such user exists.
     */
    fun findByIdAndEmail(id: Long, email: String): User?

    /**
     * Finds a [User] by their email address.
     *
     * @param email The email address of the user to find.
     * @return The user with the specified email address, or null if no such user exists.
     */
    fun findByEmail(email: String): User?

    /**
     * Finds a [User] by their id, including their list of [Recipe]s.
     * This is useful when the [User] object has been loaded with lazy fetching, and the list of recipes is needed.
     * If only the [User] object is needed, without their list of recipes, the [findById] function should be used instead.
     *
     * @param id The id of the user to find.
     * @return The user with the specified id, including their list
     */
    @Query("SELECT u FROM User u JOIN FETCH u.recipes WHERE u.id = :id")
    fun findByIdWithRecipes(@Param("id") id: Long?): User?
}