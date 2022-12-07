package de.hsflensburg.recipe_backend.users.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.recipes.entity.Rating
import de.hsflensburg.recipe_backend.recipes.entity.Favorite
import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.hibernate.annotations.CreationTimestamp
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "member")
class User (

    @Column( name = "first_name", nullable = false)
    var firstName: String,

    @Column(name = "last_name", nullable = false)
    var lastName: String,

    @Column(name = "email", nullable = false, unique = true)
    var email: String,

    @Column(name = "password", nullable = false)
    @JsonIgnore
    var password: String,

    @Column(name = "image" ,nullable = true)
    var imageUrl: String? = null,

){
    @OneToMany(mappedBy = "author", orphanRemoval = true)
    @JsonBackReference
    val recipes: MutableSet<Recipe> = mutableSetOf()

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt : Date? = null

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonManagedReference
    var favoriteRecipes: MutableSet<Favorite> = mutableSetOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var ratingsCreatedByUser: MutableSet<Rating> = mutableSetOf()
}