package de.hsflensburg.recipe_backend.categories

import de.hsflensburg.recipe_backend.categories.entity.Category
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.annotation.PostConstruct
import javax.validation.Valid
/**
 * RestController for handling Category related API calls.
 *
 * @property categoryRepository the repository for handling Category data
 */
@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryRepository: CategoryRepository,
) {

    /**
     * Creates a new Category.
     *
     * @param category the Category object to be created
     * @return the created Category object
     */
    @PostMapping
    fun createCategory(@RequestBody @Valid category: Category): Category {
        return categoryRepository.save(category)
    }

    /**
     * Retrieves a list of all categories.
     *
     * @return a list of all categories
     */
    @GetMapping
    fun getCategories(): MutableList<Category> {
        return categoryRepository.findAll()
    }

    /**
     * Retrieves a specific category by its id.
     *
     * @param id the id of the category to retrieve
     * @return the category with the specified id, wrapped in an Optional
     */
    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: Long): Optional<Category> {
        return categoryRepository.findById(id)
    }

    /**
     * Updates a category with the specified id.
     *
     * @param id the id of the category to update
     */
    @PatchMapping("/{id}")
    fun updateCategory(@PathVariable id: Long) {
        // TODO
    }

    /**
     * Initializes the CategoryController by creating an initial category.
     */
    @PostConstruct
    private fun init() {
        val category1 = Category("Vegan",1,"Vegane Rezepte")
        categoryRepository.save(category1)
        val category2 = Category("Fleisch",2)
        categoryRepository.save(category2)
        val category3 = Category("Kuchen",3)
        categoryRepository.save(category3)
        val category4 = Category("Nudeln",4)
        categoryRepository.save(category4)
        val category5 = Category("Reis",5)
        categoryRepository.save(category5)

    }
}