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
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("step_number ASC") // aufsteigend nach step_number sortieren
    var steps: MutableSet<RecipeStep> = mutableSetOf()

    @Formula(value = "(SELECT SUM(ingredient_info.amount / 100 * ingredient.calories) FROM recipe INNER JOIN recipe_step ON recipe.id = recipe_step.recipe_id INNER JOIN ingredient_info ON recipe_step.id = ingredient_info.recipe_step_id INNER JOIN ingredient ON ingredient_info.ingredient_id = ingredient.id WHERE recipe.id = id)")
    val totalCalories: Double = 0.0

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null
}

