package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.repository.RatingRepository
import de.hsflensburg.recipe_backend.recipes.repository.RecipeRepository
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import de.hsflensburg.recipe_backend.users.UserRepository
import de.hsflensburg.recipe_backend.users.entity.User
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
internal class RatingServiceTest @Autowired constructor(
    private val ratingService: RatingService,
    private val recipeService: RecipeService,
    private val ratingRepository: RatingRepository,
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository
) {

    private lateinit var author: User
    private lateinit var ingredientPasta: Ingredient
    private lateinit var ingredientTomato: Ingredient
    private lateinit var ingredientCheese: Ingredient

    @BeforeEach
    fun setup() {
        author = userRepository.save(
            User("test", "test", "a@b.de", "password")
        )

        ingredientPasta = ingredientRepository.save(
            Ingredient(LanguageSelection.English, "Pasta", 122.0, 1.0, 3.0, 4.0)
        )
        ingredientTomato = ingredientRepository.save(
            Ingredient(LanguageSelection.English, "Tomato", 45.0, 3.0, 66.0, 12.0)
        )

        ingredientCheese = ingredientRepository.save(
            Ingredient(LanguageSelection.English, "Cheese", 22.0, 6.0, 36.0, 33.0)
        )
    }

    @Test
    fun `should create Rating for Recipe`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 80.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )

        val recipe = recipeService.createRecipe(recipeDto,1)

        assertEquals(ratingRepository.findAll().size,0)
        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        assertEquals(ratingRepository.findAll().size,1)
    }


    @Test
    fun `should get average rating or 00 if no ratings exist`(){

        val author2 = userRepository.save(
            User("test", "test", "hans@b.de", "password")
        )
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 80.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )

        val recipe = recipeService.createRecipe(recipeDto,1)
        val recipeNoRating = recipeRepository.findById(recipe.id!!)
        assertEquals(0.0,recipeNoRating.get().averageRating)

        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        val recipeWithRating = recipeRepository.findById(recipe.id!!)
        assertEquals(5.0,recipeWithRating.get().averageRating)

        ratingService.rateRecipe(1,author2.id!!,recipe.id!!)
        val recipeWith2Rating = recipeRepository.findById(recipe.id!!)
        assertEquals(3.0,recipeWith2Rating.get().averageRating)
    }
    @Test
    fun `should get correct own rating value for recipe`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 80.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )

        val recipe = recipeService.createRecipe(recipeDto,1)

        assertEquals(ratingRepository.findAll().size,0)
        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        assertEquals(ratingRepository.findAll().size,1)

        val rating = ratingService.getRating(author.id!!,recipe.id!!)
        assertEquals(5,rating)
    }
    @Test
    fun `should get standard value for recipe`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 80.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )

        val recipe = recipeService.createRecipe(recipeDto,1)

        assertEquals(ratingRepository.findAll().size,0)

        val ratingNotFound = ratingService.getRating(author.id!!,recipe.id!!)
        assertEquals(3,ratingNotFound)
    }

    @Test
    fun `should delete Rating`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 80.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )

        val recipe = recipeService.createRecipe(recipeDto,1)

        assertEquals(ratingRepository.findAll().size,0)
        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        assertEquals(ratingRepository.findAll().size,1)

        ratingService.deleteRating(author.id!!,recipe.id!!)
        assertEquals(ratingRepository.findAll().size,0)
    }
}