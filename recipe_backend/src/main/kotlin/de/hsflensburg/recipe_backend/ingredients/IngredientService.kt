package de.hsflensburg.recipe_backend.ingredients

import org.springframework.stereotype.Service
import java.util.*
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