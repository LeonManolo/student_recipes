package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.recipes.entity.Favorite
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.recipes.repository.FavoriteRepository
import de.hsflensburg.recipe_backend.users.UserRepository
import de.hsflensburg.recipe_backend.users.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

/**
 * A service for managing user favorites.
 *
 * @property favoriteRepository The repository for accessing favorite records.
 * @property userService The service for managing users.
 * @property recipeService The service for managing recipes.
 */
@Service
class FavoriteService(
    private val favoriteRepository: FavoriteRepository,
    private val userService: UserService,
    private val recipeService: RecipeService,
) {
    /**
     * Favorites a recipe for the specified user.
     *
     * @param userId The ID of the user who is favoriting the recipe.
     * @param recipeId The ID of the recipe being favorited.
     * @return The favorite record that was created.
     */
    fun favoriteRecipe(userId: Long, recipeId: Long): Favorite {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)

        return favoriteRepository.save(Favorite(user, recipe))
    }

    /**
     * Unfavorites a recipe for the specified user.
     *
     * @param userId The ID of the user who is unfavoriting the recipe.
     * @param recipeId The ID of the recipe being unfavorited.
     * @return The number of records that were deleted.
     */
    @Transactional
    fun unfavoriteRecipe(userId: Long, recipeId: Long): Long {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)

        return favoriteRepository.deleteByUserAndRecipe(user, recipe)
    }

    /**
     * Gets the favorite recipes for the specified user.
     *
     * @param userId The ID of the user whose favorite recipes to get.
     * @return A list of the user's favorite recipes.
     */
    fun getFavoriteRecipes(userId: Long): List<Recipe> {
        return recipeService.getFavoriteRecipesForUser(userId)
    }
}
