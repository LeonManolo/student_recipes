package de.hsflensburg.recipe_backend.ingredients

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Type
import javax.persistence.*

@Entity
@Table(
    name = "ingredient",
    uniqueConstraints = [UniqueConstraint(columnNames = ["title", "locale"])],
)
class Ingredient(

    @Column(name = "locale", nullable = false)
    val locale: LanguageSelection,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "calories", nullable = false)
    val calories: Int,

    @Formula(value = "calories + protein")
    @JsonProperty(required = false)
    val calculation: Int,

    @Type(type = "org.hibernate.type.IntegerType")
    @Column(name = "protein", nullable = false)
    val protein: Int,

    @Column(name = "carbs",nullable = false)
    val carbohydrates: Int,

    @Column(name = "fat", nullable = false)
    val fat: Int,

    //Optional am ende am besten
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)