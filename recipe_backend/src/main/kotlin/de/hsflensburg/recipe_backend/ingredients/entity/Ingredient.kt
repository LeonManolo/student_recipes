package de.hsflensburg.recipe_backend.ingredients.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Type
import javax.persistence.*

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
    var calories: Int,

    @Column(name = "protein", nullable = false)
    var protein: Int, // TODO: vielleicht in double umwandeln

    @Column(name = "carbs",nullable = false)
    var carbohydrates: Int,

    @Column(name = "fat", nullable = false)
    var fat: Int,

) {

    //Todo gucken ob loeschbar ist
    @OneToMany(mappedBy = "ingredient", cascade = [CascadeType.ALL])
    @JsonIgnore
    var ingredientInfos: MutableSet<IngredientInfo> = mutableSetOf()

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null
}