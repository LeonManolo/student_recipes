package de.hsflensburg.recipe_backend.recipes

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "recipe")
class Recipe(

    @Column(nullable = false)
    val title : String,

    @Column(nullable = false)
    val description : String,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    /*
    @ManyToMany(mappedBy = "likedRecipes")
    @JsonIgnoreProperties("likedRecipes")
    val likes : Set<User>? = null
    */


)
