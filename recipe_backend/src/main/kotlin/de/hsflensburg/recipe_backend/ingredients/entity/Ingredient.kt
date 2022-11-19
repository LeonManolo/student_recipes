package de.hsflensburg.recipe_backend.ingredients.entity

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

    @Column(nullable = false)
    val locale: LanguageSelection,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val calories: Int,

    @Formula(value = "calories + protein")
    @JsonProperty(required = false)
    val calculation: Int,

    @Type(type = "org.hibernate.type.IntegerType")
    @Column(nullable = false)
    val protein: Int,

    @Column(nullable = false)
    val carbohydrates: Int,

    @Column(nullable = false)
    val fat: Int,

    //Optional am ende am besten
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    val id: Long? = null,
)