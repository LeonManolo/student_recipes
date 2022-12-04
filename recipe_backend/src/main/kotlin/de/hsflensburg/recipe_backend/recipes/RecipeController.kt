package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/recipes")
class RecipeController(val recipeService: RecipeService) {
    @PostMapping
    fun createRecipe(@RequestBody @Valid recipe: CreateRecipeRequestDto) {
        recipeService.createRecipe(recipe)
    }

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id:Long) : Recipe? {
        return recipeService.getRecipe(id)
    }

    @GetMapping
    fun getRecipes() : List<Recipe> {
        return recipeService.getRecipes()
    }

    @PatchMapping("/{id}")
    fun updateRecipe(@PathVariable id:Long, @RequestBody @Valid recipe: CreateRecipeRequestDto) {
        recipeService.updateRecipe(id, recipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long){
       recipeService.deleteRecipe(id)
    }
}