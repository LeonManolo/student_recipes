package de.hsflensburg.recipe_backend.recipes.repository

import de.hsflensburg.recipe_backend.recipes.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecipeRepository: JpaRepository<Recipe, Long> {



    fun findAllByOrderByPriceAsc(): List<Recipe>
    fun findAllByOrderByViewsDesc(): List<Recipe>
    fun findAllByOrderByCookTimeAsc(): List<Recipe>
    fun findAllByOrderByCreatedAtAsc(): List<Recipe>
    fun findByOrderByAverageRatingDesc(): List<Recipe>




    fun findByTotalCaloriesGreaterThanEqual(totalCalories: Double): Iterable<Recipe>


    //TODO: kann man renamen
    fun findByFavoritedBy_User_Id(id: Long): List<Recipe>


    fun findByCategories_Category_Id(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByPriceAsc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByViewsDesc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByCookTimeAsc(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByCreatedAtAsc(id: Long): List<Recipe>



    fun findByAuthor_Id(id: Long): List<Recipe>


    fun findByCategories_Category_IdOrderByAverageRatingDesc(id: Long): List<Recipe>

}