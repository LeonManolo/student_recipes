package de.hsflensburg.recipe_backend.authentication

import de.hsflensburg.recipe_backend.authentication.dto.LoginDto
import de.hsflensburg.recipe_backend.authentication.dto.RegisterDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/auth")
class AuthController { // TODO: User Service wird hier eingebunden

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDto): String {

        // TODO: User in DB speichern

        return "register"
    }


    @PostMapping("/login")
    fun login(@RequestBody body: LoginDto): String {
        // TODO: Prüfen ob der User in der DB existiert


        val issuer = "https://recipe-backend.de"

        val jwt = Jwts.builder()
            .setSubject("123")
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 Day oder 24 min?
            .signWith(SignatureAlgorithm.ES256, "secret-bitte-ändern") //TODO: sicher speichern
            .compact()

        return jwt
    }
}