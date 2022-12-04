package de.hsflensburg.recipe_backend.ingredients.dto

import de.hsflensburg.recipe_backend.shared.LanguageSelection
import de.hsflensburg.recipe_backend.shared.isValid
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CreateIngredientRequestDtoTest {

    @Test
    fun `should create a CreateIngredientRequestDto`() {
        val createIngredientRequestDto = CreateIngredientRequestDto(
            locale = LanguageSelection.German,
            title = "Test",
            calories = 0,
            protein = 0,
            carbohydrates = 0,
            fat = 0,
        )
        assertTrue(createIngredientRequestDto.isValid())
    }

    @Test
    fun `should not create an ingredient with negative values`() {
        val dto = CreateIngredientRequestDto(
            locale = LanguageSelection.German,
            title = "Test",
            calories = -1,
            protein = -1,
            carbohydrates = -1,
            fat = -1,
        )
        val de = LanguageSelection.German
        assertFalse(dto.isValid())

        val dtos = listOf(
            CreateIngredientRequestDto(de, "Test", -1, 0, 0, 0),
            CreateIngredientRequestDto(de, "Test", 0, -1, 0, 0),
            CreateIngredientRequestDto(de, "Test", 0, 0, -1, 0),
            CreateIngredientRequestDto(de, "Test", 0, 0, 0, -1),
        )
        dtos.forEach { assertFalse(it.isValid()) }
    }

    @Test
    fun `should not create an ingredient with too high values`() {
        val de = LanguageSelection.German
        val dto = CreateIngredientRequestDto(
            locale = de,
            title = "Test",
            calories = 22,
            protein = 101,
            carbohydrates = 101,
            fat = 101,
        )
        assertFalse(dto.isValid())

        val dtos = listOf(
            CreateIngredientRequestDto(de, "Test", 0, 101, 0, 0),
            CreateIngredientRequestDto(de, "Test", 0, 0, 101, 0),
            CreateIngredientRequestDto(de, "Test", 0, 0, 0, 101),
        )
        dtos.forEach { assertFalse(it.isValid()) }
    }

    @Test
    fun `should not create an ingredient with empty title`() {
        val dto = CreateIngredientRequestDto(
            locale = LanguageSelection.German,
            title = "",
            calories = 0,
            protein = 0,
            carbohydrates = 0,
            fat = 0,
        )
        assertFalse(dto.isValid())
    }

    /*
    @Test
    fun `should not create an ingredient with invalid locale`() {
        val dto = CreateIngredientRequestDto(
            locale = "invalid",
            title = "Test",
            calories = 0,
            protein = 0,
            carbohydrates = 0,
            fat = 0,
        )
        assertFalse(dto.isValid())
    }
    */
}