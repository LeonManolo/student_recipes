package de.hsflensburg.recipe_backend.authentication

import de.hsflensburg.recipe_backend.authentication.dto.LoginDto
import de.hsflensburg.recipe_backend.authentication.dto.RegisterDto
import de.hsflensburg.recipe_backend.authentication.jwt.JwtUtils
import de.hsflensburg.recipe_backend.model.User
import de.hsflensburg.recipe_backend.users.UserDetailsImpl
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(
    @Autowired private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
    private val encoder: PasswordEncoder,

) { // TODO: User Service wird hier eingebunden


    @PostMapping("/signup")
    fun registerUser(@RequestBody registerDto: RegisterDto): ResponseEntity<*>? {
        if (userRepository.findByEmail(registerDto.email) != null) { // TODO: richtige existByEmail funktion aufrufen
            return ResponseEntity.badRequest().body<Any>("Error: Username is already taken!")
        }

        // Create new user's account
        val user = User(
            "Max",
            "Mustermann",
            registerDto.email,
            "",
            encoder.encode(registerDto.password),
        )

        userRepository.save<User>(user)
        return ResponseEntity.ok<Any>("User registered successfully!")
    }


    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginDto: LoginDto): ResponseEntity<Any> {
        val authentication: Authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password))
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.getPrincipal() as UserDetailsImpl
        val jwtCookie: ResponseCookie = jwtUtils.generateJwtCookie(userDetails)
        val roles: List<String> = userDetails.authorities.stream()
            .map { item: GrantedAuthority? -> item!!.authority }
            .collect(Collectors.toList())
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).build<Any>() //TODO: change to body
                /*
            .body<Any>(
                UserInfoResponse(
                    userDetails.getId(),
                    userDetails.username,
                    userDetails.getEmail(),
                    roles
                )
            )
            */

    }

    @GetMapping("/test")
    fun test(): String {
        return "Sollte öffentlich sein"
    }


    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<*>? {
        val cookie = jwtUtils.getCleanJwtCookie()
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body<Any>("Logged out") // Todo: change
    }
}