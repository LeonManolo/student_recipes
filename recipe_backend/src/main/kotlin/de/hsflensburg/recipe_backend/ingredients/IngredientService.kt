package de.hsflensburg.recipe_backend.ingredients

import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import org.springframework.stereotype.Service

@Service
class IngredientService(private val ingredientRepository: IngredientRepository) {
    fun createIngredient(ingredient: Ingredient){
        ingredientRepository.save(ingredient)
    }

    fun getIngredient(id: Long) : Ingredient? {
        return ingredientRepository.findById(id).orElse(null);
    }

    fun getAllIngredients() : Iterable<Ingredient> {
        return ingredientRepository.findAll()
    }
}