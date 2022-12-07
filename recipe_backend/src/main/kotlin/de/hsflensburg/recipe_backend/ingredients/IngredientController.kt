package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.dto.CreateIngredientRequestDto
import de.hsflensburg.recipe_backend.ingredients.dto.toIngredient
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/ingredients")
class IngredientController (private val ingredientService: IngredientService) {

    // Ingredients mit keyword suchen
    @GetMapping
    fun getAllIngredients() : Iterable<Ingredient>{
        return ingredientService.getAllIngredients()
    }

    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id:Long): Ingredient? {
        // TODO: richtige exception werfen
        return ingredientService.getIngredient(id)
    }

    @PostMapping
    fun createIngredient(@RequestBody ingredientDto: CreateIngredientRequestDto): Ingredient {
        return ingredientService.createIngredient(ingredientDto.toIngredient())
    }

    @PatchMapping("/{id}")
    fun updateIngredient(@PathVariable id:Long, @RequestBody ingredientDto: CreateIngredientRequestDto) {
        ingredientService.updateIngredient(id, ingredientDto.toIngredient())
    }

    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable id: Long){
        ingredientService.deleteIngredient(id)
    }
}