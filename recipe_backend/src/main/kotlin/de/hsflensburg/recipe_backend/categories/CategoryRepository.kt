package de.hsflensburg.recipe_backend.categories

import de.hsflensburg.recipe_backend.categories.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}