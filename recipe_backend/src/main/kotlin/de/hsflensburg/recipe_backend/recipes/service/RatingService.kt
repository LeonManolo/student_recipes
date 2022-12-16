package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.recipes.dto.RecipeRatingResponseDto
import de.hsflensburg.recipe_backend.recipes.entity.Rating
import de.hsflensburg.recipe_backend.recipes.repository.RatingRepository
import de.hsflensburg.recipe_backend.users.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RatingService(
    private val userService: UserService,
    private val recipeService: RecipeService,
    private val ratingRepository: RatingRepository,
) {

    //Todo  limit value input to 1 - 5
    fun rateRecipe(value: Int, userId: Long, recipeId: Long) {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)

        val rating = ratingRepository.findByUserAndRecipe(user, recipe)
        if (rating != null) {
            rating.value = value
            ratingRepository.save(rating)
        } else {
            ratingRepository.save(Rating(value, user, recipe))
        }
    }

    fun updateRating(value: Int, userId: Long, recipeId: Long) {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)
        val rating = ratingRepository.findByUserAndRecipe(user, recipe)

        rating!!.value = value
        ratingRepository.save(rating)
    }

    @Transactional
    fun deleteRating(userId: Long, recipeId: Long): Long {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)

        return ratingRepository.deleteByUserAndRecipe(user, recipe)
    }

    fun getRating(userId: Long, recipeId: Long): RecipeRatingResponseDto {
        val user = userService.getUser(userId)
        val recipe = recipeService.getRecipe(recipeId)
        val rating = ratingRepository.findByUserAndRecipe(user, recipe)?.value
        return RecipeRatingResponseDto(rating ?: 0)
    }
}