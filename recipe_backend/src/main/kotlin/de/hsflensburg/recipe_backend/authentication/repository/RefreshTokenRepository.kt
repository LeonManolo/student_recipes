package de.hsflensburg.recipe_backend.authentication.repository

import de.hsflensburg.recipe_backend.authentication.entity.RefreshToken
import de.hsflensburg.recipe_backend.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository


@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken?, Long?> {
    fun findByToken(token: String?): RefreshToken?

    @Modifying
    fun deleteByUser(user: User): Int
}