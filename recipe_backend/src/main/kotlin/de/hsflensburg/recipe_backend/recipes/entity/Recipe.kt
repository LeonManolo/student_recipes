package de.hsflensburg.recipe_backend.recipes.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import de.hsflensburg.recipe_backend.categories.entity.CategoryRecipe
import de.hsflensburg.recipe_backend.ingredients.dto.IngredientResponseDto
import de.hsflensburg.recipe_backend.recipes.extension.sumIngredients
import de.hsflensburg.recipe_backend.recipes.extension.updateNutritionalData
import de.hsflensburg.recipe_backend.users.entity.User
import org.hibernate.Hibernate
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Formula
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.persistence.*
/**
 * Recipe Entity Class
 * @property title the title of the recipe
 * @property description the description of the recipe
 * @property servings the number of servings of the recipe
 * @property author the author of the recipe
 * @property price the price of the recipe
 * @property cookTime the cooking time of the recipe
 * @property id the id of the recipe
 * @property steps the steps of the recipe
 * @property createdAt the creation date of the recipe
 * @property views the number of views of the recipe
 * @property favoritedBy the list of users who have favorited the recipe
 * @property ratingsOfRecipe the list of ratings for the recipe
 * @property categories the categories the recipe belongs to
 * @property totalCalories the total calories of the recipe
 * @property totalProtein the total protein of the recipe
 * @property totalCarbohydrates the total carbohydrates of the recipe
 * @property totalFat the total fat of the recipe
 * @property ingredients the ingredients of the recipe
 */
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

    @Column(name = "price", nullable = false)
    var price: Double = 0.0,

    @Column(name = "cooking_time", nullable = false)
    var cookTime : Int = 0,
    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long? = null

    //Orhanremoval ist noetig fuer update
    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    @OrderBy("step_number ASC") // aufsteigend nach step_number sortieren
    var steps: MutableSet<RecipeStep> = mutableSetOf()

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Date? = null

    @Column(name = "views", nullable = false)
    var views: Int = 0

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    var favoritedBy: MutableSet<Favorite> = mutableSetOf()

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    var ratingsOfRecipe: MutableSet<Rating> = mutableSetOf()

    @OneToMany(mappedBy = "recipe", cascade = [CascadeType.ALL])
    var categories: MutableSet<CategoryRecipe> = mutableSetOf()

    @Formula(value = "(select coalesce(avg(rating.value),0) from rating where rating.recipe_id = id)")
    var averageRating: Double = 0.0

    //@Formula(RecipeConstants.CALORIES_FORMULA)
    var totalCalories: Double = 0.0
    var totalProtein: Double = 0.0
    var totalCarbohydrates: Double = 0.0
    var totalFat: Double = 0.0

    //@Transient
    val ingredients: MutableList<IngredientResponseDto>
        get() = sumIngredients()


    @PrePersist
    @PreUpdate
    @Transactional
    fun updateNutritionalValues() = updateNutritionalData()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Recipe

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()
}

