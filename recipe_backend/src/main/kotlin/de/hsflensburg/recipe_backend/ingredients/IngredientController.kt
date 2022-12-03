package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ingredients")
class IngredientController (val ingredientService: IngredientService) {


    // Ingredients mit keyword suchen
    @GetMapping
    fun getAllIngredients() : Iterable<Ingredient>{
        return ingredientService.getAllIngredients()
    }

    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id:Long): Ingredient? {
        return ingredientService.getIngredient(id) ?: throw Exception("Ingredient not found")
    }

    @PostMapping
    fun createIngredient(@RequestBody ingredient: Ingredient) {
        ingredientService.createIngredient(ingredient)
    }
}