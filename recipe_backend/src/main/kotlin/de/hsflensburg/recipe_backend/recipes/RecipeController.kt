package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.files.FileService
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeRatingResponseDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.entity.RecipeFilter
import de.hsflensburg.recipe_backend.recipes.service.FavoriteService
import de.hsflensburg.recipe_backend.recipes.service.RatingService
import de.hsflensburg.recipe_backend.recipes.service.RecipeService
import de.hsflensburg.recipe_backend.shared.getIdOfAuthenticatedUser
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.validation.Valid
/**
 * A `RecipeController` handles HTTP requests related to recipes.
 *
 * @param recipeService the service object to interact with the `recipe` data store
 * @param favoriteService the service object to interact with the `favorite` data store
 * @param ratingService the service object to interact with the `rating` data store
 * @param fileService the service object to interact with the file storage
 */
@RestController
@RequestMapping("/api/recipes")
class RecipeController(
    private val recipeService: RecipeService,
    private val favoriteService: FavoriteService,
    private val ratingService: RatingService,
    private val fileService: FileService,
    ) {
    /**
     * Creates a new Recipe.
     *
     * @param file the file to be uploaded
     * @param recipe the Recipe object to be created
     */
    @PostMapping(consumes = ["multipart/form-data"])
    fun createRecipe(@RequestPart("file") file: MultipartFile, @RequestPart("recipe") @Valid recipe: CreateRecipeRequestDto) {
        val image = fileService.uploadFile(file.bytes, file.originalFilename!!);
        recipe.imageUrl = image.publicUrl
        recipeService.createRecipe(recipe, getIdOfAuthenticatedUser())
    }

    /**
     * Retrieves a specific recipe by its id.
     *
     * @param id the id of the recipe to retrieve
     * @return the recipe with the specified id, or null if no such recipe exists
     */
    @GetMapping("/{id}")
    fun getRecipe(@PathVariable id:Long) : Recipe? {
        return recipeService.getRecipe(id,true)
    }

    /**
     * Retrieves a list of all recipes.
     *
     * @param sortBy optional sorting criteria for the recipes
     * @return a list of all recipes, sorted according to the specified criteria
     */

    /*
    @GetMapping
    fun getRecipes(@Valid @RequestParam("sort_by") sortBy: RecipeFilter? = null) : List<Recipe> {
        return recipeService.getRecipes(sortBy)
    }
     */

    @GetMapping
    fun getRecipes(
        @RequestParam("limit") limit: Int? = 100,
        @RequestParam("category", required = false) category: Long? = null,
        @Valid @RequestParam("sort_by") sortBy: RecipeFilter? = null
    ): List<Recipe> {
        val recipes: List<Recipe> = if (category == null){
            recipeService.getRecipes(sortBy)
        } else
            recipeService.getRecipesForCategory(sortBy, category)

        return recipes.take(limit ?: 1000)
    }

    @PatchMapping("/{id}", consumes = ["multipart/form-data"])
    fun updateRecipe(@PathVariable id:Long, @RequestPart("file") file: MultipartFile, @RequestPart("recipe") @Valid recipe: CreateRecipeRequestDto) {
        val image = fileService.uploadFile(file.bytes, file.originalFilename!!);
        recipe.imageUrl = image.publicUrl
        recipeService.updateRecipe(id, recipe, getIdOfAuthenticatedUser())
    }

    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: Long){

       recipeService.deleteRecipe(id, getIdOfAuthenticatedUser())
    }


    @PostMapping("favorites/{recipeId}")
    fun addFavorite(@PathVariable recipeId: Long) {
        val userId = getIdOfAuthenticatedUser()
        favoriteService.favoriteRecipe(userId, recipeId)
    }

    @PostMapping("favorites/unfavorite/{recipeId}")
    fun deleteFavorite(@PathVariable recipeId: Long) {
        val userId = getIdOfAuthenticatedUser()
        favoriteService.unfavoriteRecipe(userId, recipeId)
    }


    @GetMapping("myrecipes")
    fun getUserRecipes() : List<Recipe>{
        return recipeService.getRecipesOfUser(getIdOfAuthenticatedUser())
    }

    @GetMapping("favorites/ofUser")
    fun getFavoritesOfUser(): List<Recipe>{
        return favoriteService.getFavoriteRecipes(getIdOfAuthenticatedUser())
    }

    // Not in use anymore, instead use field averageRating in recipe entity
    @GetMapping("/rating/{recipeId}")
    fun getRating(@PathVariable recipeId: Long): RecipeRatingResponseDto {
        val userId = getIdOfAuthenticatedUser()
        return ratingService.getRating(userId,recipeId)
    }

    @PostMapping("/rating/{recipeId}")
    fun addRating(@PathVariable recipeId: Long, @RequestParam("rating") rating: Int) {
        val userId = getIdOfAuthenticatedUser()
        ratingService.rateRecipe(rating, userId, recipeId)
    }



}