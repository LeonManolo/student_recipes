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
/**
 * User class representing a member of the application
 * @property firstName The first name of the user
 * @property lastName The last name of the user
 * @property email The email address of the user
 * @property password The password of the user
 * @property imageUrl The URL of the user's profile image (optional)
 * @property recipes A set of recipes created by the user
 * @property createdAt The date the user account was created
 * @property id The unique ID of the user
 * @property favoriteRecipes A set of recipes marked as favorites by the user
 * @property ratingsCreatedByUser A set of ratings created by the user
 */
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
    @JsonBackReference
    @JsonIgnore
    var favoriteRecipes: MutableSet<Favorite> = mutableSetOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonBackReference
    @JsonIgnore
    var ratingsCreatedByUser: MutableSet<Rating> = mutableSetOf()
}