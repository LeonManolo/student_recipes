package de.hsflensburg.recipe_backend.categories.entity

import javax.persistence.*

//Todo many to many mit recipe
@Entity
@Table(name = "category")
class Category(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "description", nullable = true)
    var description: String? = null,

    @Column(name = "image_url", nullable = true)
    var imageUrl: String? = null,


){
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}