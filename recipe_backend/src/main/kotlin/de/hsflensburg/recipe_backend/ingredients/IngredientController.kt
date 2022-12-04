package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.dto.CreateIngredientRequestDto
import de.hsflensburg.recipe_backend.ingredients.dto.toIngredient
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
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
        // TODO: richtige exception werfen
        return ingredientService.getIngredient(id) ?: throw NotFoundException()
    }

    @PostMapping
    fun createIngredient(@RequestBody ingredientDto: CreateIngredientRequestDto): Ingredient {
        return ingredientService.createIngredient(ingredientDto.toIngredient())
    }
}