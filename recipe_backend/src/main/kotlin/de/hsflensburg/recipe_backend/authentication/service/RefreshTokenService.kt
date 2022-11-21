package de.hsflensburg.recipe_backend.authentication.service

import de.hsflensburg.recipe_backend.authentication.TokenRefreshException
import de.hsflensburg.recipe_backend.authentication.entity.RefreshToken
import de.hsflensburg.recipe_backend.authentication.repository.RefreshTokenRepository
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*


@Service
class RefreshTokenService {
    @Value("\${bezkoder.app.jwtRefreshExpirationMs}")
    private val refreshTokenDurationMs: Long? = null

    @Autowired
    private val refreshTokenRepository: RefreshTokenRepository? = null

    @Autowired
    private val userRepository: UserRepository? = null
    fun findByToken(token: String?): RefreshToken? {
        return refreshTokenRepository!!.findByToken(token)
    }

    fun createRefreshToken(userId: Long): RefreshToken {
        var refreshToken = RefreshToken(
            user = userRepository!!.getReferenceById(userId),
            token = UUID.randomUUID().toString(),
            expiryDate = Instant.now().plusMillis(refreshTokenDurationMs!!)
        )
        refreshToken = refreshTokenRepository!!.save(refreshToken)
        return refreshToken
    }

    fun verifyExpiration(token: RefreshToken): RefreshToken {
        if (token.expiryDate!! < Instant.now()) {
            refreshTokenRepository!!.delete(token)
            throw TokenRefreshException(token.token, "Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    @Transactional
    fun deleteByUserId(userId: Long): Int {
        return refreshTokenRepository!!.deleteByUser(userRepository!!.findById(userId).get())
    }
}
