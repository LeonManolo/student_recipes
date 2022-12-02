package de.hsflensburg.recipe_backend.users.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.hibernate.annotations.CreationTimestamp
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "member")
class User (

    @Column( name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "last_name", nullable = false)
    val lastName: String,

    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    @JsonIgnore
    val password: String,

    @OneToMany(mappedBy = "author")
    @JsonBackReference
    val recipes: MutableSet<Recipe> = mutableSetOf(),

    //---------- Optional am ende am besten ----------------

    @Column(name = "image" ,nullable = true)
    val imageUrl: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null,



    /*
    @ManyToMany
    @JoinTable(
        name = "recipe_like",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "recipe_id")]
    )
    @JsonIgnoreProperties("likes")
    val likedRecipes : List<Recipe> = mutableListOf()
    */

)