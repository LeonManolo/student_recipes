package de.hsflensburg.recipe_backend.authentication.dto

import de.hsflensburg.recipe_backend.shared.isValid

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RegisterRequestDtoTest {

    @Test
    fun `should be a valid loginDto`() {
        val validDto = RegisterRequestDto(
            firstName = "abc",
            lastName = "abc",
            email = "abc@abc.de",
            password = "123",
            imageUrl = null,
        )
        assertTrue(validDto.isValid())
    }

    @Test
    fun `should not be valid if the email is not an email`() {
        val invalidDto = RegisterRequestDto(
            firstName = "abc",
            lastName = "abc",
            email = "abc@",
            password = "123",
            imageUrl = null,
        )
        assertFalse(invalidDto.isValid())

        val invalidDto2 = RegisterRequestDto(
            firstName = "abc",
            lastName = "abc",
            email = "",
            password = "123",
            imageUrl = null,
        )
        assertFalse(invalidDto2.isValid())

        val validDto = RegisterRequestDto(
            firstName = "abc",
            lastName = "abc",
            email = "abc@abc.com",
            password = "123",
            imageUrl = null,
        )
        assertTrue(validDto.isValid())
    }

    @Test
    fun `should not be valid`() {
        val invalidDto = RegisterRequestDto(
            firstName = "abc",
            lastName = "abc",
            email = "abc@ab.de",
            password = "",
            imageUrl = null,
        )
        assertFalse(invalidDto.isValid())

        val invalidDto2 = RegisterRequestDto(
            firstName = "",
            lastName = "abc",
            email = "abc@ad.de",
            password = "123",
        )
        assertFalse(invalidDto2.isValid())

        val invalidDto3 = RegisterRequestDto(
            firstName = "abc",
            lastName = "",
            email = "abc@abc.com",
            password = "123",
            imageUrl = null,
        )
        assertFalse(invalidDto3.isValid())
    }
}