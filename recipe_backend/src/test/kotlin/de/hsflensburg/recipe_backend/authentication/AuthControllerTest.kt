package de.hsflensburg.recipe_backend.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import de.hsflensburg.recipe_backend.authentication.dto.LoginRequestDto
import de.hsflensburg.recipe_backend.authentication.dto.RegisterRequestDto
import de.hsflensburg.recipe_backend.users.entity.User
import de.hsflensburg.recipe_backend.users.UserRepository
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(refresh = RefreshMode.AFTER_EACH_TEST_METHOD) // Für in memory postgresql db, DOCKER muss installiert sein
internal class AuthControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val userRepository: UserRepository,
    private val encoder: PasswordEncoder,
    ) {



    @Value("\${recipe.app.jwtCookieName}")
    private lateinit var jwtCookie: String

    @Value("\${recipe.app.jwtRefreshCookieName}")
    private lateinit var jwtRefreshCookie: String

    @Test
    fun `should successfully register a user`() {
        val registerDto = RegisterRequestDto("Hans", "Wurst", "abc@abc.de","password")
        // when/then
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto)
        }.andDo {
            print()
        }.andExpect {
            status { isCreated() }
        }

        val registerDto2 = RegisterRequestDto("Hans", "Wurst", "abc@ss.de","password")
        // when/then
        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto2)
        }.andDo {
            print()
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `should not register a user with an existing email`() {
        val registerDto = RegisterRequestDto("Hans", "Wurst", "hans@wurst.de", "password")

        userRepository.save(User("Hans", "Wurst", "hans@wurst.de", "password"))

        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto)
        }.andExpect {
            status { isConflict() }
        }
    }

    @Test
    fun `should not register with bad credentials like a bad email`() {
        val registerDto = RegisterRequestDto("Hans", "Wurst", "hans@", "password")

        mockMvc.post("/api/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should successfully login a user return jwt cookies`() {
        val hashedPassword = encoder.encode("password")
        userRepository.save(User("Hans", "Wurst", "a@b.com",hashedPassword))

        val loginDto = LoginRequestDto("a@b.com", "password")

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDto)
        }.andExpect {
            /*
            cookie {
                exists(jwtCookie)
                exists(jwtRefreshCookie)
            }
            */

                jsonPath("$.token") {
                    exists()
                }
            status { isOk() }
        }
    }

    @Test
    fun `should not login a user with a wrong password and therefore also should not set jwt cookies`() {
        val hashedPassword = encoder.encode("otherPassword")
        userRepository.save(User("Hans", "Wurst", "a@b.com",hashedPassword))

        val loginDto = LoginRequestDto("a@b.com", "password")

        mockMvc.post("/api/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginDto)
        }.andExpect {
            cookie {
                doesNotExist(jwtCookie)
                doesNotExist(jwtRefreshCookie)
            }
            status { isUnauthorized() }
        }
    }
}