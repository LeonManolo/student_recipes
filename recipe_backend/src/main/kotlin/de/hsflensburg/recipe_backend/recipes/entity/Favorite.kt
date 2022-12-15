package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*
/**
 *Entity representing a favorite recipe for a [User].
 * @property Entity Marks this class as a JPA entity.
 * @property Table Specifies the details of the table that this entity will be persisted to.
 * @property ManyToOne Specifies a many-to-one relationship with the [User] entity.
 * @property JoinColumn Specifies the details of the column that will be used to join with the [User] entity.
 * @property Column Specifies details for a column in the database table.
 * @property CreationTimestamp Automatically sets the value of this property to the current date and time when the entity is created.
 * @property Id Marks this property as the primary key for the entity.
 * @property GeneratedValue Specifies the generation strategy to be used for the values of this property.
 * @constructor Creates a new [Favorite] instance with the specified [user] and [recipe].
 */
@Entity
@Table(name = "favorite",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "recipe_id"])]
)
class Favorite (

    @ManyToOne(optional = false)
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(optional = false)
    @JsonManagedReference
    @JoinColumn(name = "recipe_id", nullable = false)
    var recipe: Recipe? = null
        ){

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

}