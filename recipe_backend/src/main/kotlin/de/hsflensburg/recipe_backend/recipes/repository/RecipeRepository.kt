package de.hsflensburg.recipe_backend.recipes.repository

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecipeRepository: JpaRepository<Recipe, Long> {



    fun findAllByOrderByPriceDesc(): List<Recipe>
    fun findAllByOrderByViewsDesc(): List<Recipe>
    fun findAllByOrderByCookTimeDesc(): List<Recipe>
    fun findAllByOrderByCreatedAtDesc(): List<Recipe>
    fun findByOrderByAverageRatingDesc(): List<Recipe>




    fun findByTotalCaloriesGreaterThanEqual(totalCalories: Double): Iterable<Recipe>


    //TODO: kann man renamen
    fun findByFavoritedBy_User_Id(id: Long): List<Recipe>


    fun findByCategories_Category_Id(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByPriceDesc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByViewsDesc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByCookTimeDesc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByCreatedAtDesc(id: Long): List<Recipe>



    fun findByAuthor_Id(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByAverageRatingDesc(id: Long): List<Recipe>

}