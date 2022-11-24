package de.hsflensburg.recipe_backend.authentication.dto

import de.hsflensburg.recipe_backend.shared.isValid
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class LoginRequestDtoTest {

    @Test
    fun `should be a valid loginDto`() {
        val loginDto = LoginRequestDto(
            email = "abc@abc.de",
            password = "123"
        )

        assertFalse(loginDto.isValid())
    }

    @Test
    fun `should not be valid if the email is not an email`() {
        val validDto = LoginRequestDto(
            email = "abc@abc",
            password = "ABc123__#"
        )
        assertTrue(validDto.isValid())

        val validDto2 = LoginRequestDto(
            email = "abc@abc.de",
            password = "ABc123__#"
        )
        assertTrue(validDto2.isValid())

        val invalidDto = LoginRequestDto(
            email = "abc",
            password = "ABc123__#"
        )
        assertFalse(invalidDto.isValid())

        val invalidDto2 = LoginRequestDto(
            email = "",
            password = "ABc123__#"
        )
        assertFalse(invalidDto.isValid())
    }

    @Test
    fun `should not be valid if the password is blank`() {
        val validDto = LoginRequestDto(
            email = "abc@abc",
            password = "a"
        )
        assertTrue(validDto.isValid())

        val invalidDto = LoginRequestDto(
            email = "abc@abc.de",
            password = ""
        )
        assertFalse(invalidDto.isValid())
    }
}