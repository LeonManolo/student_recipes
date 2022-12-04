package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import javax.persistence.*

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
    @OneToMany(mappedBy = "recipeStep", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("created_at")
    var ingredients: MutableSet<IngredientInfo> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
