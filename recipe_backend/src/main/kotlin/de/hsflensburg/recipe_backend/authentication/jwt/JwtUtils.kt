package de.hsflensburg.recipe_backend.authentication.jwt

import de.hsflensburg.recipe_backend.authentication.dto.JwtResponseDto
import de.hsflensburg.recipe_backend.users.entity.User
import de.hsflensburg.recipe_backend.authentication.entity.UserDetailsImpl
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.util.WebUtils
import java.security.Key
import java.security.SignatureException
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * This class is responsible for creating and validating JWTs.
 * It is used by the [AuthTokenFilter] to validate
 * incoming requests.
 * It is also used by the [AuthService] to create
 * JWTs for successful authentication.
 */
@Component
class JwtUtils {
    @Value("\${recipe.app.jwtSecret}")
    private lateinit var jwtSecret: String

    @Value("\${recipe.app.jwtExpirationMs}")
    private val jwtExpirationMs = 0

    @Value("\${recipe.app.jwtCookieName}")
    private lateinit var jwtCookie: String

    @Value("\${recipe.app.jwtRefreshCookieName}")
    private lateinit var jwtRefreshCookie: String



    fun generateJwtCookie(userPrincipal: UserDetailsImpl): ResponseCookie {
        val jwt = generateTokenFromUsername(userPrincipal.username)
        return generateCookie(jwtCookie, jwt, "/api")
    }

    fun generateJwtCookie(user: User): ResponseCookie {
        val jwt = generateTokenFromUsername(user.email)
        return generateCookie(jwtCookie, jwt, "/api")
    }

    fun generateRefreshJwtCookie(refreshToken: String): ResponseCookie? {
        return generateCookie(jwtRefreshCookie, refreshToken, "/api/auth/refreshtoken")
    }

    fun getJwtFromCookies(request: HttpServletRequest): String? {
        return getCookieValueByName(request, jwtCookie)
    }

    fun getJwtFromHeader(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")?.replace("Bearer ", "")
    }

    fun generateJwtTokenHeader(user: User): JwtResponseDto {
        val jwt = generateTokenFromUsername(user.email)
        return JwtResponseDto(jwt)
    }

    fun getJwtRefreshFromCookies(request: HttpServletRequest): String? {
        return getCookieValueByName(request, jwtRefreshCookie)
    }

    fun getCleanJwtCookie(): ResponseCookie {
        return ResponseCookie.from(jwtCookie, "").path("/api").build() // TODO: prüfen ob richtig
    }

    fun getCleanJwtRefreshCookie(): ResponseCookie {
        return ResponseCookie.from(jwtRefreshCookie, "").path("/api/auth/refreshtoken").build() //TODO: prüfen
    }

    fun getUserNameFromJwtToken(token: String?): String {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            val keyBytes = Decoders.BASE64.decode(jwtSecret)
            val key: Key = Keys.hmacShaKeyFor(keyBytes)
            Jwts.parserBuilder().setSigningKey(key).build().parse(authToken)
            return true
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token: {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT token is expired: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("JWT token is unsupported: {}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT claims string is empty: {}", e.message)
        }
        return false
    }

    fun generateTokenFromUsername(username: String?): String {
        val keyBytes = Decoders.BASE64.decode(jwtSecret)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(key)
            .compact()
    }

    private fun generateCookie(name: String, value: String, path: String): ResponseCookie {
        return ResponseCookie.from(name, value).path(path).maxAge((24 * 60 * 60).toLong()).httpOnly(true).build()
    }

    private fun getCookieValueByName(request: HttpServletRequest, name: String): String? {
        //request.getHeader("")
        val cookie = WebUtils.getCookie(request, name)
        return cookie?.value
    }


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtUtils::class.java)
    }
}