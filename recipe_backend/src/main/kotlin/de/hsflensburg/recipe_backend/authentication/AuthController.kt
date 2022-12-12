package de.hsflensburg.recipe_backend.authentication

import de.hsflensburg.recipe_backend.authentication.dto.LoginRequestDto
import de.hsflensburg.recipe_backend.authentication.dto.RegisterRequestDto
import de.hsflensburg.recipe_backend.authentication.entity.RefreshToken
import de.hsflensburg.recipe_backend.authentication.entity.UserDetailsImpl
import de.hsflensburg.recipe_backend.authentication.jwt.JwtUtils
import de.hsflensburg.recipe_backend.authentication.service.RefreshTokenService
import de.hsflensburg.recipe_backend.users.entity.User
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
    private val encoder: PasswordEncoder,
    private val refreshTokenService: RefreshTokenService,

    ) { // TODO: User Service wird hier eingebunden


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@Valid @RequestBody registerDto: RegisterRequestDto) {
        if (userRepository.findByEmail(registerDto.email) != null) { // TODO: richtige existByEmail funktion aufrufen
            throw ResponseStatusException(HttpStatus.CONFLICT, "Email already exists!")
        }

        // Create new user's account
        val user = User(
            registerDto.firstName,
            registerDto.lastName,
            registerDto.email,
            encoder.encode(registerDto.password),
        )

        userRepository.save<User>(user)
    }


    @PostMapping("/login")
    fun authenticateUser(@Valid @RequestBody loginDto: LoginRequestDto): ResponseEntity<*>? {
        val authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(loginDto.email, loginDto.password))
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.principal as UserDetailsImpl
        val jwtCookie = jwtUtils.generateJwtCookie(userDetails)
        val roles = userDetails.authorities.stream()
            .map { item: GrantedAuthority? -> item!!.authority }
            .collect(Collectors.toList())
        val refreshToken: RefreshToken = refreshTokenService.createRefreshToken(userDetails.getId())
        val jwtRefreshCookie = jwtUtils.generateRefreshJwtCookie(refreshToken.token!!)
        // TODO: hier auf authorization header umstellen
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body<Any>(
                "Signed in successfully!"
            )
    }

    @GetMapping("/test")
    fun test(): UserDetailsImpl {
        return SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
    }


    @PostMapping("/signout")
    fun logoutUser(): ResponseEntity<*>? {
        val principle = SecurityContextHolder.getContext().authentication.principal
        if (principle.toString() !== "anonymousUser") {
            val userId = (principle as UserDetailsImpl).getId()
            refreshTokenService.deleteByUserId(userId)
        }
        val jwtCookie = jwtUtils.getCleanJwtCookie()
        val jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie()
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
            .body<Any>("You've been signed out!")
    }

    @PostMapping("/refresh_token")
    fun refreshToken(request: HttpServletRequest): ResponseEntity<*>? {
        val refreshToken = jwtUtils.getJwtRefreshFromCookies(request)
        val token = refreshTokenService.findByToken(refreshToken)

        return if (!refreshToken.isNullOrEmpty() && token != null) {
            val tokenResult = refreshTokenService.findByToken(refreshToken)
            val user = refreshTokenService.verifyExpiration(tokenResult!!).user!!
            val jwtCookie: ResponseCookie = jwtUtils.generateJwtCookie(user)
            ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshToken)
                .body("Refreshed token successfully!")


        } else ResponseEntity.badRequest().body<Any>("Refresh Token is empty!")

    }
}