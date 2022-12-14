package de.hsflensburg.recipe_backend.recipes.entity

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Size
import kotlin.math.min

/**
 * A `Rating` represents a user's rating for a specific recipe.
 * It has a value between 1 and 5 and is associated with a user and a recipe.
 *
 * @property value The value of this rating, a positive integer between 1 and 5.
 * @property user The user who created this rating.
 * @property recipe The recipe that this rating is for.
 */
@Entity
@Table(name = "rating",
        uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "recipe_id"])]
)
class Rating (
    //Todo value check einbauen
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
    val id: Long? = null
}