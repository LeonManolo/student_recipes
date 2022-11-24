package de.hsflensburg.recipe_backend.authentication

import com.fasterxml.jackson.databind.ObjectMapper
import de.hsflensburg.recipe_backend.authentication.dto.RegisterRequestDto
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase // Für in memory postgresql db, DOCKER muss installiert sein
internal class AuthControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
) {

    @Test
    fun `should successfully register a user`() {
        val registerDto = RegisterRequestDto("Hans", "Wurst", "abc@abc.de","password")
        // when/then
        mockMvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto)
        }.andDo {
            print()
        }.andExpect {
            status { isCreated() }
        }

        val registerDto2 = RegisterRequestDto("Hans", "Wurst", "abc@ss.de","password")
        // when/then
        mockMvc.post("/api/auth/signup") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(registerDto2)
        }.andDo {
            print()
        }.andExpect {
            status { isCreated() }
        }
    }

}