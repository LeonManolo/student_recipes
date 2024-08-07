package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.recipes.repository.FavoriteRepository
import de.hsflensburg.recipe_backend.recipes.repository.RatingRepository
import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.entity.Favorite
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import de.hsflensburg.recipe_backend.recipes.repository.RecipeRepository
import de.hsflensburg.recipe_backend.recipes.service.repos_for_testing.IngredientInfoRepository
import de.hsflensburg.recipe_backend.recipes.service.repos_for_testing.RecipeStepRepository
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import de.hsflensburg.recipe_backend.shared.isValid
import de.hsflensburg.recipe_backend.users.UserRepository
import de.hsflensburg.recipe_backend.users.entity.User
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.JpaRepository

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
internal class RecipeServiceTest @Autowired constructor(
    private val recipeService: RecipeService,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeStepRepository: RecipeStepRepository,
    private val ingredientInfoRepository: IngredientInfoRepository,
    private val favoriteRepository: FavoriteRepository,
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
    fun `should delete user and his recipes`() {
        assertEquals(userRepository.findAll().size, 1)
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

        assertEquals(recipeRepository.findAll().size, 1)
        assertEquals(recipeStepRepository.findAll().size, 2)
        assertEquals(ingredientInfoRepository.findAll().size, 2)

        userRepository.deleteById(author.id!!)

        assertEquals(recipeRepository.findAll().size, 0)
        assertEquals(userRepository.findAll().size, 0)
        assertEquals(recipeStepRepository.findAll().size, 0)
        assertEquals(ingredientInfoRepository.findAll().size, 0)

    }

    @Test
    fun `delete Recipe deletes everything connected`() {
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

        favoriteRepository.save(Favorite(author, recipe))

        assertEquals(recipeRepository.findAll().size, 1)
        assertEquals(recipeStepRepository.findAll().size, 2)
        assertEquals(ingredientInfoRepository.findAll().size, 2)
        assertEquals(favoriteRepository.findAll().size, 1)

        recipeService.deleteRecipe(recipe.id!!,1)

        assertEquals(recipeRepository.findAll().size, 0)
        assertEquals(recipeStepRepository.findAll().size, 0)
        assertEquals(ingredientInfoRepository.findAll().size, 0)
        assertEquals(favoriteRepository.findAll().size, 0)

    }

    @Test
    fun `test calculated properties`() {

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
        val id = recipeService.createRecipe(recipeDto,1).id!!
        val result = recipeRepository.findById(id).get()
        println(result.totalCalories)
        val totalCalories =
            (recipeDto.steps[0].ingredients[0].amount / 100 * ingredientPasta.calories) + (recipeDto.steps[1].ingredients[0].amount / 100 * ingredientTomato.calories)
        assertEquals(totalCalories, result.totalCalories)

        val ingredient = ingredientRepository.findById(ingredientPasta.id!!)
        ingredient.get().calories = 500.0;
        ingredientRepository.save(ingredient.get())

        val totalCalories2 = recipeRepository.findById(result.id!!).get().totalCalories
        val totalCalories2expected =
            (recipeDto.steps[0].ingredients[0].amount / 100 * 500) + (recipeDto.steps[1].ingredients[0].amount / 100 * ingredientTomato.calories)
        assertEquals(totalCalories2expected, totalCalories2)
    }

    @Test
    fun `should create a new recipe`() {
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 50.0, "g"),
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            )
        )
        assertTrue(recipeDto.isValid())
        val result1 = recipeService.createRecipe(recipeDto,1)
        val result = recipeRepository.findById(result1.id!!).get()

        assertEquals(recipeDto.title, result.title)
        assertEquals(recipeDto.description, result.description)
        assertEquals(recipeDto.servings, result.servings)
        assertEquals(recipeDto.steps.size, result.steps.size)
        assertEquals(recipeDto.steps[0].title, result.steps.toList()[0].title) // jetzt geht es?
        assertEquals(recipeDto.steps[0].description, result.steps.toList()[0].description)
        assertEquals(recipeDto.steps[0].ingredients.size, result.steps.toList()[0].ingredients.size)
        assertEquals(
            recipeDto.steps[0].ingredients[0].ingredientId,
            result.steps.toList()[0].ingredients.toList()[0].ingredient!!.id
        )
        assertEquals(recipeDto.steps[0].ingredients[0].amount, result.steps.toList()[0].ingredients.toList()[0].amount)
        assertEquals(recipeDto.steps[0].ingredients[0].unit, result.steps.toList()[0].ingredients.toList()[0].unit)
        assertEquals(recipeDto.steps[1].title, result.steps.toList()[1].title)
        assertEquals(recipeDto.steps[1].description, result.steps.toList()[1].description)
        assertEquals(recipeDto.steps[1].ingredients.size, result.steps.toList()[1].ingredients.size)
        assertEquals(
            recipeDto.steps[1].ingredients[0].ingredientId,
            result.steps.toList()[1].ingredients.toList()[0].ingredient!!.id
        )
        assertEquals(recipeDto.steps[1].ingredients[0].amount, result.steps.toList()[1].ingredients.toList()[0].amount)
        assertEquals(recipeDto.steps[1].ingredients[0].unit, result.steps.toList()[1].ingredients.toList()[0].unit)
    }

    @Test
    fun `should add up all ingredients as a list as a prop on the Recipe`(){
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 50.0, "g"),
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                        IngredientInfoDto(ingredientPasta.id!!, 100.0, "g"),

                        )
                )
            )
        )
        val id = recipeService.createRecipe(recipeDto,1).id!!
        val recipe = recipeService.getRecipe(id)
        val ingredients = recipe.ingredients
        println(ingredients)
        assertEquals(ingredients.size, 2)
        assertEquals(ingredients[0].id, ingredientPasta.id)
        assertEquals(ingredients[0].calories,ingredientPasta.calories * 1.5)
        assertEquals(ingredients[1].calories,ingredientTomato.calories * 0.5)


    }

    @Test
    fun `should update an existing recipe (basically just keeping the id)`() {
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            listOf(
                RecipeStepDto(
                    "Pasta", "cook pasta", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 50.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Tomato", "cut tomato", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 50.0, "g"),
                    )
                )
            ),
            null,
            listOf<Long>(1)
        )
        assertTrue(recipeDto.isValid())
        val result = recipeService.createRecipe(recipeDto,author.id!!)

        val updatedRecipeDto = CreateRecipeRequestDto(
            "title2",
            "description2",
            3,
            listOf(
                RecipeStepDto(
                    "Pasta2", "cook pasta 2", listOf(
                        IngredientInfoDto(ingredientPasta.id!!, 50.0, "g"), // 50.0 * noch nicht beachtet!!!
                    )
                ),
                RecipeStepDto(
                    "Cheese", "Add some cheese", listOf(
                        IngredientInfoDto(ingredientCheese.id!!, 50.0, "g"),
                    )
                ),
                RecipeStepDto(
                    "Tomato 2", "cut tomato 2", listOf(
                        IngredientInfoDto(ingredientTomato.id!!, 100.0, "g"),
                    )
                )
            ), null,
            listOf<Long>(2)
        )
        assertTrue(updatedRecipeDto.isValid())

        //TODO: reihenfolge inconstent
        //TODO: aktuelles datum abspeichern und schauen ob es einträge vor diesem datum gibt
        val id = recipeService.updateRecipe(result.id!!, updatedRecipeDto,1).id!!
        val updatedResult = recipeRepository.findById(id).get()
        assertTrue(updatedResult.categories.first().id == 2L)
        assertTrue(updatedResult.title == "title2")
        assertTrue(updatedResult.description == "description2")
        assertTrue(updatedResult.steps.size == 3)
        assertEquals(updatedResult.steps.first().title, "Pasta2")
        assertTrue(updatedResult.steps.first().description == "cook pasta 2")
        assertTrue(updatedResult.steps.first().ingredients.size == 1)
        assertTrue(updatedResult.steps.first().ingredients.first().amount == 50.0)
        assertTrue(updatedResult.steps.first().ingredients.first().unit == "g")
        assertTrue(updatedResult.steps.toList()[1].title == "Cheese")
        assertTrue(updatedResult.steps.toList()[1].description == "Add some cheese")
        assertTrue(updatedResult.steps.toList()[1].ingredients.size == 1)
        assertTrue(updatedResult.steps.toList()[1].ingredients.first().amount == 50.0)
        assertTrue(updatedResult.steps.toList()[1].ingredients.first().unit == "g")
        assertTrue(updatedResult.steps.last().title == "Tomato 2")
        assertTrue(updatedResult.steps.last().description == "cut tomato 2")
        assertTrue(updatedResult.steps.last().ingredients.size == 1)
        assertTrue(updatedResult.steps.last().ingredients.first().amount == 100.0)
        assertTrue(updatedResult.steps.last().ingredients.first().unit == "g")

        // check if the old recipe data is still there
        val oldRecipeStep = recipeStepRepository.findById(result.steps.first().id!!)
        assertTrue(oldRecipeStep.isEmpty)
        val oldRecipeStep2 = recipeStepRepository.findById(result.steps.last().id!!)
        assertTrue(oldRecipeStep2.isEmpty)
        val recipeStepCount = recipeStepRepository.count()
        assertEquals(3, recipeStepCount)

        val oldIngredientInfo = ingredientInfoRepository.findById(result.steps.first().ingredients.first().id!!)
        assertTrue(oldIngredientInfo.isEmpty)
        val oldIngredientInfo2 = ingredientInfoRepository.findById(result.steps.last().ingredients.first().id!!)
        assertTrue(oldIngredientInfo2.isEmpty)
        val ingredientInfoCount = ingredientInfoRepository.count()
        assertEquals(3, ingredientInfoCount)
    }



    @Test
    fun `should get all recipes of user`(){
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

        val recipe1 = recipeService.createRecipe(recipeDto,author.id!!)

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
            )
        )

        val recipe2 = recipeService.createRecipe(recipeDto2,author.id!!)

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
            )
        )

        val recipe3 = recipeService.createRecipe(recipeDto3,author.id!!)

        val recipes = recipeService.getRecipesOfUser(author.id!!)

        assertEquals(3, recipes.size)
    }

}