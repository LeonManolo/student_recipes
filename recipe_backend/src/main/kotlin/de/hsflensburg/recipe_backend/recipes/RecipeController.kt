package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.entity.RecipeFilter
import de.hsflensburg.recipe_backend.recipes.service.FavoriteService
import de.hsflensburg.recipe_backend.recipes.service.RatingService
import de.hsflensburg.recipe_backend.recipes.service.RecipeService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/recipes")
class RecipeController(
    private val recipeService: RecipeService,
    private val favoriteService: FavoriteService,
    private val ratingService: RatingService,
    ) {
    @PostMapping
    fun createRecipe(@RequestBody @Valid recipe: CreateRecipeRequestDto) {
        recipeService.createRecipe(recipe)
    }

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id:Long) : Recipe? {
        return recipeService.getRecipe(id)
    }

    @GetMapping
    fun getRecipes(@Valid @RequestParam sortBy: RecipeFilter) : List<Recipe> {
        return recipeService.getRecipes(sortBy)
    }

    @PatchMapping("/{id}")
    fun updateRecipe(@PathVariable id:Long, @RequestBody @Valid recipe: CreateRecipeRequestDto) {
        recipeService.updateRecipe(id, recipe)
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long){
       recipeService.deleteRecipe(id)
    }

    @PostMapping("favorites/{recipeId}")
    fun addFavorite(@PathVariable recipeId: Long) {
        val userId = 1L
        favoriteService.favoriteRecipe(userId, recipeId)
    }
}