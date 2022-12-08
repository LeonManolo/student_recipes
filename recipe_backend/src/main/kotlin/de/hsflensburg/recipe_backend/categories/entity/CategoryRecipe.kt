package de.hsflensburg.recipe_backend.categories.entity

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import javax.persistence.*

@Entity
@Table(name = "category_recipe")
class CategoryRecipe(

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    var category: Category? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    var recipe: Recipe? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
}