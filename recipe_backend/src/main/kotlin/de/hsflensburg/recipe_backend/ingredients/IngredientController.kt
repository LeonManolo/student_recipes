package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.dto.CreateIngredientRequestDto
import de.hsflensburg.recipe_backend.ingredients.dto.toIngredient
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.web.bind.annotation.*

/**
 * A RestController that handles requests for the Ingredient resource.
 *
 * @property ingredientService The IngredientService instance used to handle Ingredient-related tasks.
 */
@RestController
@RequestMapping("/api/ingredients")
class IngredientController(private val ingredientService: IngredientService) {

    /**
     * Handles a GET request for all Ingredient objects.
     *
     * @param keyword The keyword used to search specific ingredients by title.
     * @return An Iterable containing all Ingredient objects in the database.
     */
    @GetMapping
    fun getAllIngredients(@RequestParam("keyword") keyword: String? = null): Iterable<Ingredient> {
        return ingredientService.getAllIngredients(keyword)
    }

    /**
     * Endpoint that returns the ingredient with the specified ID.
     *
     * @param id The ID of the ingredient to be retrieved.
     * @return The Ingredient object with the specified ID.
     */
    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id: Long): Ingredient {
        return ingredientService.getIngredient(id)
    }

    /**
     * Endpoint that creates a new ingredient.
     *
     * @param ingredientDto A DTO object that contains the information for the new ingredient.
     * @return The newly created Ingredient object.
     */
    @PostMapping
    fun createIngredient(@RequestBody ingredientDto: CreateIngredientRequestDto): Ingredient {
        return ingredientService.createIngredient(ingredientDto.toIngredient())
    }

    /**
     * Endpoint that updates an existing ingredient.
     *
     * @param id The ID of the ingredient to be updated.
     * @param ingredientDto A DTO object that contains the updated information for the ingredient.
     */
    @PatchMapping("/{id}")
    fun updateIngredient(@PathVariable id: Long, @RequestBody ingredientDto: CreateIngredientRequestDto) {
        ingredientService.updateIngredient(id, ingredientDto.toIngredient())
    }

    /**
     * Endpoint that deletes an existing ingredient.
     *
     * @param id The ID of the ingredient to be deleted.
     */
    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable id: Long) {
        ingredientService.deleteIngredient(id)
    }
}