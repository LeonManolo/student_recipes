package de.hsflensburg.recipe_backend.ingredients.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

/**
 * An Entity class that represents the IngredientInfo table in the database (its only connected to the recipe entity).
 * This class is used to store information about the amount and unit of an ingredient
 * used in a recipe step.
 *
 * @property amount The amount of the ingredient used in the recipe step.
 * @property unit The unit of measurement for the ingredient (e.g. "ml", "grams").
 * @property recipeStep The RecipeStep object that this IngredientInfo belongs to.
 * @property ingredient The actual Ingredient that is used.
 */
@Entity
@Table(name = "ingredient_info")
class IngredientInfo(

    @Column(name = "amount", nullable = false)
    var amount: Double,

    @Column(name = "unit", nullable = false)
    var unit: String,

    @ManyToOne
    @JoinColumn(name = "recipe_step_id", nullable = false)
    @JsonBackReference
    var recipeStep: RecipeStep? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ingredient_id", nullable=false)
    @JsonManagedReference
    var ingredient: Ingredient? = null,


) {
    @PrePersist
    @PreUpdate
    @PreRemove
    fun updateTotalCalories() {
        recipeStep!!.recipe!!.updateNutritionalValues()
    }

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}
