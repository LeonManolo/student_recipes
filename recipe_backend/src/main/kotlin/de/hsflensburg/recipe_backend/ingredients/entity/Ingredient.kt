package de.hsflensburg.recipe_backend.ingredients.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Type
import org.springframework.transaction.annotation.Transactional
import javax.persistence.*

/**
 * Represents an ingredient that can be used in a recipe.
 *
 * @property locale The language in which the ingredient is defined.
 * @property title The title of the ingredient.
 * @property calories The number of calories per 100 grams/ml of the ingredient.
 * @property protein The amount of protein per 100 grams/ml of the ingredient.
 * @property carbohydrates The amount of carbohydrates per 100 grams of the ingredient.
 * @property fat The amount of fat per 100 grams of the ingredient.
 * @property ingredientInfos The [IngredientInfo] objects that reference this ingredient.
 * @property id The unique ID of the ingredient.
 */
@Entity
@Table(
    name = "ingredient",
    //uniqueConstraints = [UniqueConstraint(columnNames = ["title", "locale"])],
)
class Ingredient(

    //TODO: nicht veränderbar machen (generell bei jeder Tabelle mit locale)
    @Column(name = "locale", nullable = false, updatable = false)
    var locale: LanguageSelection,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "calories", nullable = false)
    var calories: Double,

    @Column(name = "protein", nullable = false)
    var protein: Double,

    @Column(name = "carbs",nullable = false)
    var carbohydrates: Double,

    @Column(name = "fat", nullable = false)
    var fat: Double,

) {

    /**
     * If a changes occurs in the ingredient, the ingredient infos have to be updated inside the Recipe entity.
     */
    @PrePersist
    @PreUpdate
    @PreRemove
    @Transactional
    fun updateTotalCalories() {
        ingredientInfos.forEach { it.updateTotalCalories() }
    }

    //Todo gucken ob loeschbar ist
    @OneToMany(mappedBy = "ingredient", cascade = [CascadeType.ALL])
    @JsonIgnore
    var ingredientInfos: MutableSet<IngredientInfo> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}