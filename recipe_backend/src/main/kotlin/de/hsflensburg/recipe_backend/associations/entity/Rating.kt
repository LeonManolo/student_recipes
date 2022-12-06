package de.hsflensburg.recipe_backend.associations.entity

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Size
import kotlin.math.min

// This class represents a rating for a recipe.
// It contains the rating value, the user who made the rating, and the recipe being rated.
@Entity
@Table(name = "rating",
        uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "recipe_id"])]
)
class Rating (
    //Todo value check einbauen
    // The value of this rating, a positive integer between 1 and 5.
    @Column(name = "value", nullable = false)
    var value: Int,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    var recipe: Recipe
        ) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

}