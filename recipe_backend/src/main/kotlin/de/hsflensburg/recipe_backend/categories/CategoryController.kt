package de.hsflensburg.recipe_backend.categories

import de.hsflensburg.recipe_backend.categories.entity.Category
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryRepository: CategoryRepository,
) {

    @PostMapping
    fun createCategory(@RequestBody @Valid category: Category): Category {
        return categoryRepository.save(category)
    }

    @GetMapping
    fun getCategories(): MutableList<Category> {
        return categoryRepository.findAll()
    }

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }

    @PatchMapping("/{id}")
    fun updateCategory(@PathVariable id: Long) {
        // TODO
    }
}