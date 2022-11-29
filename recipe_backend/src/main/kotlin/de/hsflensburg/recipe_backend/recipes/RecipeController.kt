package de.hsflensburg.recipe_backend.recipes

import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController(val recipeService: RecipeService) {
    @PostMapping
    fun createRecipe(@RequestBody recipe: Recipe) {
        recipeService.createRecipe(recipe)
    }

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id:Long) : Recipe? {
        return recipeService.getRecipe(id)
    }
    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long){
       recipeService.deleteRecipe(id)
    }
}