package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.categories.entity.CategoryRecipe
import de.hsflensburg.recipe_backend.recipes.constants.RecipeConstants
import de.hsflensburg.recipe_backend.users.entity.User
import org.hibernate.Hibernate
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
    //Orhanremoval ist noetig fuer update
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("step_number ASC") // aufsteigend nach step_number sortieren
    var steps: MutableSet<RecipeStep> = mutableSetOf()

    @Formula(RecipeConstants.CALORIES_FORMULA)
    val totalCalories: Double = 0.0

    @Column(name ="like_count")
    var likeCount: Int = 0

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    var favoritedBy: MutableSet<Favorite> = mutableSetOf()

    @OneToMany(mappedBy = "recipe",cascade = [CascadeType.ALL])
    var ratingsOfRecipe: MutableSet<Rating> = mutableSetOf()

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    var categories: MutableSet<CategoryRecipe> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Recipe

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}

