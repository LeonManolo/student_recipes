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
            calories = 0.0,
            protein = .0,
            carbohydrates = .0,
            fat = .0,
        )
        assertTrue(createIngredientRequestDto.isValid())
    }

    @Test
    fun `should not create an ingredient with negative values`() {
        val dto = CreateIngredientRequestDto(
            locale = LanguageSelection.German,
            title = "Test",
            calories = -1.0,
            protein = -1.0,
            carbohydrates = -1.0,
            fat = -1.0,
        )
        val de = LanguageSelection.German
        assertFalse(dto.isValid())

        val dtos = listOf(
            CreateIngredientRequestDto(de, "Test", -1.0, 0.0, 0.0, 0.0),
            CreateIngredientRequestDto(de, "Test", 0.0, -1.0, 0.0, 0.0),
            CreateIngredientRequestDto(de, "Test", 0.0, 0.0, -1.0, 0.0),
            CreateIngredientRequestDto(de, "Test", .0, .0, 0.0, -1.0),
        )
        dtos.forEach { assertFalse(it.isValid()) }
    }

    @Test
    fun `should not create an ingredient with too high values`() {
        val de = LanguageSelection.German
        val dto = CreateIngredientRequestDto(
            locale = de,
            title = "Test",
            calories = 22.0,
            protein = 101.0,
            carbohydrates = 101.0,
            fat = 101.0,
        )
        assertFalse(dto.isValid())

        val dtos = listOf(
            CreateIngredientRequestDto(de, "Test", 0.0, 101.0, .0, .0),
            CreateIngredientRequestDto(de, "Test", .0, .0, 101.0, .0),
            CreateIngredientRequestDto(de, "Test", .0, .0, .0, 101.0),
        )
        dtos.forEach { assertFalse(it.isValid()) }
    }

    @Test
    fun `should not create an ingredient with empty title`() {
        val dto = CreateIngredientRequestDto(
            locale = LanguageSelection.German,
            title = "",
            calories = 0.0,
            protein = 0.0,
            carbohydrates = 0.0,
            fat = 0.0,
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