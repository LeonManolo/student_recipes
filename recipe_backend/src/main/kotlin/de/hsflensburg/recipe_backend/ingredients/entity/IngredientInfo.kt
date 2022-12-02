package de.hsflensburg.recipe_backend.ingredients.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import javax.persistence.*


@Entity
@Table(name = "ingredient_info")
class IngredientInfo(

    @Column(name = "amount", nullable = false)
    val amount: Int,

    @Column(name = "unit", nullable = false)
    val unit: String,

    @ManyToOne
    @JoinColumn(name = "recipe_step_id", nullable = false)
    @JsonBackReference
    val recipeStep: RecipeStep? = null,

    @ManyToOne
    @JoinColumn(name="ingredient_id", nullable=false)
    @JsonManagedReference
    val ingredient: Ingredient? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)
