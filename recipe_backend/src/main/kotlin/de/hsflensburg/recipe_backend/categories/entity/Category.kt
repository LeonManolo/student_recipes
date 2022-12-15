package de.hsflensburg.recipe_backend.categories.entity

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import javax.persistence.*

/**
 * Entity representing a category of items in the application
 *
 * @property title The title of the category
 * @property description A description of the category
 * @property imageUrl A URL to an image representing the category
 * @property recipes A set of recipes that belong to the category
 * @property id The ID of the category in the database
 */
@Entity
@Table(name = "category")
class Category(
    @Column(name = "title", nullable = false)
    var title: String,

    @Id
    @Column(name = "id")
    val id: Long,

    @Column(name = "description", nullable = true)
    var description: String? = null,

    @Column(name = "image_url", nullable = true)
    var imageUrl: String? = null,

){
    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL])
    var recipes: MutableSet<CategoryRecipe> = mutableSetOf()


}