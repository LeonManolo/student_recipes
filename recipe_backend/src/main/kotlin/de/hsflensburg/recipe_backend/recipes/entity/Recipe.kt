package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.users.entity.User
import javax.persistence.*

@Entity
@Table(name = "recipe")
open class Recipe(

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "servings", nullable = false)
    val servings: Int,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    val author: User,

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var steps: MutableSet<RecipeStep> = mutableSetOf(),

    /*
    @ManyToMany(mappedBy = "likedRecipes")
    @JsonIgnoreProperties("likedRecipes")
    val likes : Set<User>? = null
    */

    /*
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinTable(
        joinColumns = [JoinColumn(name = "recipe_id")],
        inverseJoinColumns = [JoinColumn(name = "ingredient_id")],
    )
    @JsonManagedReference
    val ingredients: Set<Ingredient>?,

     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)
