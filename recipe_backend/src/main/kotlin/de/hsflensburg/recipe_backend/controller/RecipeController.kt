package de.hsflensburg.recipe_backend.controller

import de.hsflensburg.recipe_backend.model.Recipe
import de.hsflensburg.recipe_backend.model.User
import de.hsflensburg.recipe_backend.repo.RecipeRepository
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/recipes")
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