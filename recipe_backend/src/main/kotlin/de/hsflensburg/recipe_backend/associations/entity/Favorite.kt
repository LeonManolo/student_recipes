package de.hsflensburg.recipe_backend.associations.entity

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import de.hsflensburg.recipe_backend.users.entity.User
import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "favorite",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "recipe_id"])]
)
class Favorite (

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null,

    @ManyToOne(optional = false)
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