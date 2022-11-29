package de.hsflensburg.recipe_backend.users

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
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
    val password: String,

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