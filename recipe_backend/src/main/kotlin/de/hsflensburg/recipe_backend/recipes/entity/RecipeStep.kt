package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import javax.persistence.*

//TODO: eventuell nummerien für die Schritte
@Entity
@Table(name = "recipe_step")
class RecipeStep (
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "step_number", nullable = true)
    val stepNumber: Int,

    @OneToMany(mappedBy = "recipeStep", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var ingredients: MutableSet<IngredientInfo> = mutableSetOf(),

    @ManyToOne() // vlt hier cascade type all
    @JoinColumn(name="recipe_id", nullable=false)
    //@Column(name = "recipe_id", nullable = false)
    @JsonBackReference
    val recipe: Recipe? = null,

    @Column(name = "image_url", nullable = true)
    val imageUrl: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
)
