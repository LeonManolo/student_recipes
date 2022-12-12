package de.hsflensburg.recipe_backend.authentication.jwt

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception thrown when the jwt token is invalid or expired.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
class TokenRefreshException(token: String?, message: String?) :
    RuntimeException(String.format("Failed for [%s]: %s", token, message)) {
    companion object {
        private const val serialVersionUID = 1L
    }
}