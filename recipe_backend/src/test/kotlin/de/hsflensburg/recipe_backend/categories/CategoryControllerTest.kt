package de.hsflensburg.recipe_backend.categories

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.entity.RecipeFilter
import de.hsflensburg.recipe_backend.recipes.repository.FavoriteRepository
import de.hsflensburg.recipe_backend.recipes.service.FavoriteService
import de.hsflensburg.recipe_backend.recipes.service.RecipeService
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import de.hsflensburg.recipe_backend.users.UserRepository
import de.hsflensburg.recipe_backend.users.entity.User
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
internal class CategoryTest@Autowired constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
    private val ingredientRepository: IngredientRepository,
    private val favoriteService: FavoriteService,
    private val recipeService: RecipeService,
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
    fun `should create recipe with category`() {
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
            ),
            null,
            listOf<Long>(1,2,3)
        )

        val recipe = recipeService.createRecipe(recipeDto, author.id!!)

        val recipeFetched = recipeService.getRecipe(recipe.id!!)
        assertEquals(3,recipeFetched.categories.size)
        println(recipeFetched.categories.toList()[0].category?.title)
        println(recipeFetched.categories.toList()[1].category?.title)
        println(recipeFetched.categories.toList()[2].category?.title)

        val shouldContain = recipeService.getRecipesForCategory(null,1)
        assertEquals(1,shouldContain.size)
        val shouldEmpty = recipeService.getRecipesForCategory(null,4)
        assertEquals(0,shouldEmpty.size)
    }

    @Test
    @Transactional
    fun `should order by filter and get for category`(){
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
            ), null,
            emptyList(),
            10.0,
            5

        )

        val id = recipeService.createRecipe(recipeDto,author.id!!).id!!

        val recipeDto2 = CreateRecipeRequestDto(
            "title2",
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
            ),null,
            listOf<Long>(1),
            20.0,
            1
        )

        val id2 = recipeService.createRecipe(recipeDto2,author.id!!).id!!

        val recipeDto3 = CreateRecipeRequestDto(
            "title3",
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
            ),null,
            listOf<Long>(1),
            3.0,
            20
        )

        val id3 = recipeService.createRecipe(recipeDto3,author.id!!).id!!

        val recipesOrderByPriceDec = recipeService.getRecipes(RecipeFilter.CHEAP)
        assertEquals(3,recipesOrderByPriceDec.size)
        assertTrue(recipesOrderByPriceDec[0].id == id2)
        assertTrue(recipesOrderByPriceDec[1].id == id)
        assertTrue(recipesOrderByPriceDec[2].id == id3)

        val check = recipeService.getRecipesForCategory(null,1)
        assertEquals(2,check.size)

        val recipesPriceDescCat1 = recipeService.getRecipesForCategory(RecipeFilter.CHEAP,1)
        assertEquals(2,recipesPriceDescCat1.size)
        assertTrue(recipesPriceDescCat1[0].id == id2 )
        assertTrue(recipesPriceDescCat1[1].id == id3 )
    }


}