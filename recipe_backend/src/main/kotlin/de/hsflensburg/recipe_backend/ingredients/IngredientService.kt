package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

/**
 * The IngredientService class represents a Spring service class that provides
 * methods for creating, retrieving, updating, and retrieving ingredients from
 * an IngredientRepository.
 */
@Service
class IngredientService(private val ingredientRepository: IngredientRepository) {
    /**
     * Saves a new ingredient in the repository
     * and returns the saved ingredient.
     *
     * @param ingredient the ingredient to save
     * @return the saved ingredient
     */
    fun createIngredient(ingredient: Ingredient): Ingredient {
        return ingredientRepository.save(ingredient)
    }

    /**
     * Retrieves an ingredient with a specific ID from
     * the repository. If no ingredient with the specified ID is found, an error
     * is thrown.
     *
     * @param id the ID of the ingredient to retrieve
     * @return the ingredient with the specified ID
     * @throws ResponseStatusException if no ingredient with the specified ID is found
     */
    fun getIngredient(id: Long) : Ingredient {
        return ingredientRepository.findById(id).orElseThrow {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient with id $id not found")
        }
    }

    /**
     * The getAllIngredients() method retrieves all ingredients from the repository
     * and returns them as an Iterable.
     *
     * @param keyword the keyword to search for
     * @return an Iterable of all ingredients in the repository
     */
    fun getAllIngredients(keyword: String? = null): Iterable<Ingredient> {
        if(keyword != null) {
            return ingredientRepository.findAllByTitleContainingIgnoreCase(keyword)
        }
        return ingredientRepository.findAll()
    }

    /**
     * The updateIngredient() method updates an existing ingredient in the
     * repository based on the specified ID and the new ingredient data. The
     * updated ingredient is then saved in the repository and returned.
     *
     * @param id the ID of the ingredient to update
     * @param ingredient the new ingredient data
     * @return the updated ingredient
     */
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

    /**
     * Deletes an ingredient with a specific ID
     * from the repository.
     *
     * @param id the ID of the ingredient to delete
     */
    fun deleteIngredient(id: Long) {
        ingredientRepository.deleteById(id)
    }
}
