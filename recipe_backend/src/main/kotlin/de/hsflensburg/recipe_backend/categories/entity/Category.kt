package de.hsflensburg.recipe_backend.categories.entity

import javax.persistence.*

@Entity
@Table(name = "category")
class Category(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = true)
    val description: String? = null,

    @Column(name = "image_url", nullable = true)
    val imageUrl: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
)