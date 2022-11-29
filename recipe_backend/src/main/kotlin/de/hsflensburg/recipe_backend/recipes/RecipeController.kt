package de.hsflensburg.recipe_backend.recipes

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController(val recipeRepository: RecipeRepository) {
    @PostMapping
    fun createRecipe(@RequestBody recipe: Recipe) {
        recipeRepository.save(recipe)
    }

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id:Long) : Optional<Recipe> {
        return recipeRepository.findById(id)
    }
    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long){
        recipeRepository.deleteById(id)
    }
}