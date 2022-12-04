package de.hsflensburg.recipe_backend.ingredients.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*


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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}
