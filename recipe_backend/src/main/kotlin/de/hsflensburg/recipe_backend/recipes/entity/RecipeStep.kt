package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import javax.persistence.*
/**
 * RecipeStep is a class that represents a single step in a recipe.
 *
 * @property title The title of the recipe step.
 * @property description The description of the recipe step.
 * @property stepNumber The number of the recipe step in the overall recipe.
 * @property recipe The recipe that the recipe step belongs to.
 * @property imageUrl The URL of an image associated with the recipe step.
 * @property ingredients A set of ingredients used in the recipe step.
 * @property id The unique ID of the recipe step.
 */
@Entity
@Table(name = "recipe_step")
class RecipeStep (
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "step_number", nullable = false)
    var stepNumber: Int,

    @ManyToOne
    @JoinColumn(name="recipe_id", nullable=false)
    @JsonBackReference
    var recipe: Recipe? = null,

    @Column(name = "image_url", nullable = true)
    var imageUrl: String? = null,

) {

    @PrePersist
    @PreUpdate
    @PreRemove
    fun updateTotalCalories() {
        recipe!!.updateNutritionalValues()
    }

    @OneToMany(mappedBy = "recipeStep", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("created_at")
    var ingredients: MutableSet<IngredientInfo> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
