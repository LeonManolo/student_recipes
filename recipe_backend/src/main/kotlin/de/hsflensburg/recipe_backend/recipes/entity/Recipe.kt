package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.users.entity.User
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
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    var steps: MutableSet<RecipeStep> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null
}

