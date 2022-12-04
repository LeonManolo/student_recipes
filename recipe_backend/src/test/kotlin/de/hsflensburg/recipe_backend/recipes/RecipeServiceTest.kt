package de.hsflensburg.recipe_backend.recipes

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.ingredients.entity.IngredientInfo
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.entity.RecipeStep
import de.hsflensburg.recipe_backend.shared.LanguageSelection
import de.hsflensburg.recipe_backend.shared.isValid
import de.hsflensburg.recipe_backend.users.UserRepository
import de.hsflensburg.recipe_backend.users.entity.User
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

internal interface RecipeStepRepository : JpaRepository<RecipeStep, Long> {
    fun findByRecipeId(recipeId: Long): List<RecipeStep>
}

internal interface IngredientInfoRepository : JpaRepository<IngredientInfo, Long> {
}

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
@Transactional
internal class RecipeServiceTest @Autowired constructor(
    private val recipeService: RecipeService,
    private val recipeRepository: RecipeRepository,
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeStepRepository: RecipeStepRepository,
    private val ingredientInfoRepository: IngredientInfoRepository,
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
    fun `test calculated properties`() {

        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            author.id!!,
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
            )
        )
        val result = recipeService.createRecipe(recipeDto)
        println(result.totalCalories)
        println((ingredientPasta.calories + ingredientTomato.calories))
        //assertTrue(result.totalCalories == ingredientPasta.calories + ingredientTomato.calories)
    }

    @Test
    fun `should create a new recipe`() {
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            author.id!!,
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
        val result1 = recipeService.createRecipe(recipeDto)
        val result = recipeRepository.findById(result1.id!!).get()

        assertEquals(recipeDto.title, result.title)
        assertEquals(recipeDto.description, result.description)
        assertEquals(recipeDto.servings, result.servings)
        assertEquals(recipeDto.authorId, result.author.id)
        assertEquals(recipeDto.steps.size, result.steps.size)
        assertEquals(recipeDto.steps[0].title, result.steps.toList()[0].title) // jetzt geht es?
        assertEquals(recipeDto.steps[0].description, result.steps.toList()[0].description)
        assertEquals(recipeDto.steps[0].ingredients.size, result.steps.toList()[0].ingredients.size)
        assertEquals(recipeDto.steps[0].ingredients[0].ingredientId, result.steps.toList()[0].ingredients.toList()[0].ingredient!!.id)
        assertEquals(recipeDto.steps[0].ingredients[0].amount, result.steps.toList()[0].ingredients.toList()[0].amount)
        assertEquals(recipeDto.steps[0].ingredients[0].unit, result.steps.toList()[0].ingredients.toList()[0].unit)
        assertEquals(recipeDto.steps[1].title, result.steps.toList()[1].title)
        assertEquals(recipeDto.steps[1].description, result.steps.toList()[1].description)
        assertEquals(recipeDto.steps[1].ingredients.size, result.steps.toList()[1].ingredients.size)
        assertEquals(recipeDto.steps[1].ingredients[0].ingredientId, result.steps.toList()[1].ingredients.toList()[0].ingredient!!.id)
        assertEquals(recipeDto.steps[1].ingredients[0].amount, result.steps.toList()[1].ingredients.toList()[0].amount)
        assertEquals(recipeDto.steps[1].ingredients[0].unit, result.steps.toList()[1].ingredients.toList()[0].unit)
    }

    @Test
    fun `should update an existing recipe (basically just keeping the id)`() {
        val recipeDto = CreateRecipeRequestDto(
            "title",
            "description",
            2,
            author.id!!,
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
            )
        )
        assertTrue(recipeDto.isValid())
        val result = recipeService.createRecipe(recipeDto)

        val updatedRecipeDto = CreateRecipeRequestDto(
            "title2",
            "description2",
            3,
            author.id!!,
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
            )
        )
        assertTrue(updatedRecipeDto.isValid())

        //TODO: reihenfolge inconstent
        //TODO: aktuelles datum abspeichern und schauen ob es einträge vor diesem datum gibt
        val updatedResult = recipeService.updateRecipe(result.id!!, updatedRecipeDto)
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

}