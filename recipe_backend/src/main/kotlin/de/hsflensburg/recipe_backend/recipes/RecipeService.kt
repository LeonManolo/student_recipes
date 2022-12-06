package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.associations.FavoriteRepository
import de.hsflensburg.recipe_backend.associations.RatingRepository
import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.associations.entity.Favorite
import de.hsflensburg.recipe_backend.associations.entity.Rating
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import de.hsflensburg.recipe_backend.users.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.Valid
import javax.validation.constraints.Size

@Service
@Transactional // muss rein damit @Formula funktioniert
class RecipeService(
    private val favoriteRepository: FavoriteRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val userRepository: UserRepository,
    private val ratingRepository: RatingRepository,
) {

    //TODO: exception handling
    fun createRecipe(recipe: CreateRecipeRequestDto): Recipe {
        val recipeToSave = Recipe(
            title = recipe.title,
            description = recipe.description,
            servings = recipe.servings,
            author = userRepository.findById(recipe.authorId).get(),
        )
        val savedRecipe = recipeRepository.save(recipeToSave)
        println(recipeToSave.id)
        println(savedRecipe.id)
        recipe.steps.mapIndexed { index, step ->
            val recipeStep = RecipeStep( // wieso kriegt recipeStep die recipe_id automatisch?
                stepNumber = index,
                title = step.title,
                description = step.description,
                recipe = savedRecipe,
                imageUrl = step.imageUrl
            )
            savedRecipe.steps.add(recipeStep)
            recipeRepository.save(savedRecipe)
            savedRecipe.steps.last().ingredients = step.ingredients.map { ingredient ->
                IngredientInfo(
                    recipeStep = savedRecipe.steps.last(),
                    amount = ingredient.amount,
                    unit = ingredient.unit,
                    ingredient = ingredientRepository.findById(ingredient.ingredientId).get()
                )
            }.toMutableSet()

        }
        return recipeRepository.save(savedRecipe)

    }

    fun getRecipe(id: Long): Recipe? {
        return recipeRepository.findById(id).orElse(null)
    }

    fun getRecipes(): List<Recipe> {
        return recipeRepository.findAll()
    }

    fun deleteRecipe(id: Long) {
        recipeRepository.deleteById(id)
    }

    fun favoriteRecipe(userId: Long, recipeId: Long){
        favoriteRepository.save(Favorite(userRepository.findById(userId).get(),recipeRepository.findById(recipeId).get()))
    }

    fun getFavoritesForUser(userId: Long): List<Recipe> {
        return recipeRepository.findByFavoritedBy_User_Id(userId)
    }

    fun unfavoriteRecipe(userId: Long, recipeId: Long): Long {
        return  favoriteRepository.deleteByUserAndRecipe(userRepository.findById(userId).get(),recipeRepository.findById(recipeId).get())
        //return favoriteRepository.deleteByUser_IdAndRecipe_Id(userId,recipeId)
    }

    //Todo  limit value input to 1 - 5
    fun rateRecipe(value: Int, userId: Long,recipeId: Long){
        ratingRepository.save(Rating(value,userRepository.findById(userId).get(),recipeRepository.findById(recipeId).get()))
    }

    fun deleteRating(userId: Long, recipeId: Long):Long{
        return ratingRepository.deleteByUserAndRecipe(userRepository.findById(userId).get(),recipeRepository.findById(recipeId).get())
    }



    //ToDO alles

    fun updateRecipe(id: Long, recipeDTO: CreateRecipeRequestDto): Recipe {
        val recipe = recipeRepository.findById(id).orElseThrow { Exception("Recipe not found") }

        recipe.steps.clear()

        recipeDTO.steps.mapIndexed { index, step ->
            val recipeStep = RecipeStep(
                stepNumber = index,
                title = step.title,
                description = step.description,
                recipe = recipe,
                imageUrl = step.imageUrl
            )
            recipeStep.ingredients = step.ingredients.map { ingredient ->
                IngredientInfo(
                    recipeStep = recipeStep,
                    amount = ingredient.amount,
                    unit = ingredient.unit,
                    ingredient = ingredientRepository.findById(ingredient.ingredientId).get()
                )
            }.toMutableSet()
            recipe.steps.add(recipeStep)
        }

        recipe.title = recipeDTO.title
        recipe.description = recipeDTO.description
        recipe.servings = recipeDTO.servings
        recipe.author = userRepository.findById(recipeDTO.authorId).get()

        val result = recipeRepository.save(recipe)
        return result

    }
}