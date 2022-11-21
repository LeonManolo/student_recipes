package de.hsflensburg.recipe_backend.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "users")
class User (

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val imageUrl: String,

    @Column(nullable = false)
    val password: String,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null,

    //Optional am ende am besten
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

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