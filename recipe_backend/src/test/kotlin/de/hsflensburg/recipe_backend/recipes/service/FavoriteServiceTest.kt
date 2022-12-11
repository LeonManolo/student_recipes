package de.hsflensburg.recipe_backend.recipes.service

import de.hsflensburg.recipe_backend.ingredients.IngredientRepository
import de.hsflensburg.recipe_backend.ingredients.entity.Ingredient
import de.hsflensburg.recipe_backend.recipes.dto.CreateRecipeRequestDto
import de.hsflensburg.recipe_backend.recipes.dto.IngredientInfoDto
import de.hsflensburg.recipe_backend.recipes.dto.RecipeStepDto
import de.hsflensburg.recipe_backend.recipes.entity.Favorite
import de.hsflensburg.recipe_backend.recipes.repository.FavoriteRepository
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
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureEmbeddedDatabase(refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD)
internal class FavoriteServiceTest @Autowired constructor(
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
    fun likeRecipe(){
        val author2 = userRepository.save(
            User("test", "test", "aaaaa@b.de", "password")
        )

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

        val id = recipeService.createRecipe(recipeDto).id!!

        val recipeDto2 = CreateRecipeRequestDto(
            "title2",
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

        val id2 = recipeService.createRecipe(recipeDto2).id!!

        val recipeDto3 = CreateRecipeRequestDto(
            "title3",
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

        val id3 = recipeService.createRecipe(recipeDto3).id!!

        favoriteService.favoriteRecipe(author.id!!,id)
        favoriteService.favoriteRecipe(author.id!!, id3)

        favoriteService.favoriteRecipe(author2.id!!,id2)


        var likedRecipe = favoriteRepository.findByUser_Id(author.id!!)
        assertEquals(likedRecipe.size,2)

        var likedRecipes2 = favoriteRepository.findByUser_Id(author2.id!!)
        assertEquals(likedRecipes2.size,1)

    }

    @Test
    //@Transactional
    fun unlike(){
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
        val id = recipe.id!!

        val recipeDto2 = CreateRecipeRequestDto(
            "title2",
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

        val id2 = recipeService.createRecipe(recipeDto2).id!!

        favoriteService.favoriteRecipe(author.id!!,id)
        favoriteService.favoriteRecipe(author.id!!,id2)

        assertEquals(favoriteRepository.findAll().size,2)

        // benoetigt vielleicht hierduch transaction
        //favoriteRepository.deleteByUser_IdAndRecipe_Id(author.id!!,id)
        //favoriteRepository.deleteByUserAndRecipe(author,recipe)
        favoriteService.unfavoriteRecipe(author.id!!,id)

        val remaining = favoriteRepository.findAll()
        assertEquals(remaining.size,1)
        assertEquals(remaining[0].recipe?.id,id2)
    }

    @Test
    @Transactional
    fun `cascade should have an influence on saving likes`(){

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

        // saven wird nicht gebraucht wenn es eine Transactional ist. adden zum set reicht aus
        recipe.favoritedBy.add(Favorite(author,recipe))
        // falls keine Transaction dann muss save aufgerufen werden.
        //recipeRepository.save(recipe)

        assertEquals(favoriteRepository.findAll().size,1)
    }
}