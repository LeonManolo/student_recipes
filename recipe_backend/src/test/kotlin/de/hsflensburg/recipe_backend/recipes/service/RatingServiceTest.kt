package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.repository.RatingRepository
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
            Ingredient(LanguageSelection.English, "Pasta", 122, 1, 3, 4)
        )
        ingredientTomato = ingredientRepository.save(
            Ingredient(LanguageSelection.English, "Tomato", 45, 3, 66, 12)
        )

        ingredientCheese = ingredientRepository.save(
            Ingredient(LanguageSelection.English, "Cheese", 22, 6, 36, 33)
        )
    }

    @Test
    fun `should create Rating for Recipe`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            author.id!!,
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

        val recipe = recipeService.createRecipe(recipeDto)

        assertEquals(ratingRepository.findAll().size,0)
        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        assertEquals(ratingRepository.findAll().size,1)
    }

    @Test
    fun `should delete Rating`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            author.id!!,
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

        val recipe = recipeService.createRecipe(recipeDto)

        assertEquals(ratingRepository.findAll().size,0)
        ratingService.rateRecipe(5,author.id!!, recipe.id!!)
        assertEquals(ratingRepository.findAll().size,1)

        ratingService.deleteRating(author.id!!,recipe.id!!)
        assertEquals(ratingRepository.findAll().size,0)
    }
}