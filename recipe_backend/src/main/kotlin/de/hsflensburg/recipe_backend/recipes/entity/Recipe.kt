package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.fasterxml.jackson.annotation.JsonProperty
import de.hsflensburg.recipe_backend.users.entity.User
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Formula
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "recipe")
class Recipe(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "servings", nullable = false)
    var servings: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    var author: User,

) {
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    @OrderBy("step_number DESC")
    var steps: MutableSet<RecipeStep> = mutableSetOf()

    //@Formula(value = "(SELECT SUM(r.calories) FROM recipe_step r WHERE r.recipe_id = id)")
    var totalCalories: Int = 0

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null
}

