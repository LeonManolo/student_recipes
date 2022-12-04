package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class IngredientService(private val ingredientRepository: IngredientRepository) {
    fun createIngredient(ingredient: Ingredient): Ingredient {
        return ingredientRepository.save(ingredient)
    }

    fun getIngredient(id: Long) : Ingredient {
        return ingredientRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id $id not found")
        }
    }

    fun getAllIngredients() : Iterable<Ingredient> {
        return ingredientRepository.findAll()
    }

    fun updateIngredient(id: Long, ingredient: Ingredient) : Ingredient {
        val ingredientToUpdate = getIngredient(id)
        ingredientToUpdate.let {
            it.title = ingredient.title
            it.calories = ingredient.calories
            it.carbohydrates = ingredient.carbohydrates
            it.fat = ingredient.fat
            it.protein = ingredient.protein
            return ingredientRepository.save(it)
        }
    }
}