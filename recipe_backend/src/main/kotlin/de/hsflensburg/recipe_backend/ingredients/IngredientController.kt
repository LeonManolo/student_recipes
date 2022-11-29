package de.hsflensburg.recipe_backend.ingredients

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ingredients")
class IngredientController (val ingredientService: IngredientService) {

    @GetMapping
    fun getAllRecipes() : Iterable<Ingredient>{
        return ingredientService.getAllIngredients()
    }

    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id:Long): Ingredient? {
        return ingredientService.getIngredient(id)
    }

    @PostMapping
    fun create(@RequestBody ingredient: Ingredient) {
        ingredientService.createIngredient(ingredient)
    }
}