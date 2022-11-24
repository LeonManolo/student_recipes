package de.hsflensburg.recipe_backend.authentication

import de.hsflensburg.recipe_backend.authentication.jwt.TokenRefreshException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.*


@RestControllerAdvice
class TokenControllerAdvice {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleTokenRefreshException(ex: TokenRefreshException, request: WebRequest): String {
        return HttpStatus.FORBIDDEN.value().toString() + " " + ex.message
        /*
        return ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            Date(),
            ex.message,
            request.getDescription(false)
        )
        */

    }
}
