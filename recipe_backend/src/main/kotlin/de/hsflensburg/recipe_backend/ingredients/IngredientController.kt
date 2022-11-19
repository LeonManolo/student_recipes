package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.data.jpa.domain.Specification
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/ingredients")
class IngredientController (val ingredientRepo: IngredientRepository) {

    @GetMapping
    fun findAll(): Iterable<Ingredient> {
        return ingredientRepo.findByCalculationGreaterThanEqual(20)
    }

    @PostMapping
    fun create(@RequestBody ingredient: Ingredient) {
        ingredientRepo.save(ingredient)
    }
}