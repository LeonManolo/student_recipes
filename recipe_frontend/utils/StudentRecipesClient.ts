import Cookies from "js-cookie";
import CreateIngredientRequestDto from "./dto/CreateIngredientRequestDto";
import CreateRecipeRequestDto from "./dto/CreateRecipeRequestDto";
import IngredientDto from "./dto/IngredientDto";
import LoginRequestDto from "./dto/LoginRequestDto";
import RecipeResponseDto from "./dto/RecipeResponseDto";
import RegisterRequestDto from "./dto/RegisterRequestDto";
import { getCookie } from "cookies-next";
import UpdateUserRequestDto from "./dto/UpdateUserRequestDto";
import UserDto from "./dto/UserDto";
import { RecipeFilter } from "./dto/RecipeFilter";
/**
 * This class contains all functions that are necessary for all of the
 * API requests. To do this, the functions use the associated dto's.
 */
export default class StudentRecipesClient {
  private readonly BASE_URL = "https://sea-turtle-app-hqisk.ondigitalocean.app";
  private DEFAULT_HEADER = {
    "Content-Type": "application/json",
    Authorization: `Bearer ${getCookie("token")}`,
  };
/**
 * Searches for ingredients by a keyword.
 * @param keyword Any string.
 * @returns A list of ingredients matching the keyword.
 */
  async getIngredients(keyword?: string): Promise<IngredientDto[]> {
    let url = this.BASE_URL + "/api/ingredients";
    if (keyword) {
      url += "?keyword=" + keyword;
    }
    const result = await fetch(url, {
      headers: this.setHeader(),
    });
    const ingredients: IngredientDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, ingredients);
  }
/**
 * Searches for a specific ingredient by id.
 * @param id Any number.
 * @returns The ingredient with its corresponding attributes matching the id.
 */
  async getIngredient(id: number): Promise<IngredientDto> {
    const result = await fetch(`${this.BASE_URL}/api/ingredients${id}`, {
      headers: this.DEFAULT_HEADER,
    });
    const ingredient: IngredientDto = await result.json();
    return await this.returnIfSuccessElseError(result, ingredient);
  }
/**
 * Creates a new ingredient.
 * @param createIngredient A selfmade data transfer object.
 * @return The created ingredient.
 */
  async createIngredient(createIngredient: CreateIngredientRequestDto): Promise<IngredientDto> {
    const json = JSON.stringify(createIngredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    const ingredient: IngredientDto = await result.json();
    return await this.returnIfSuccessElseError(result, ingredient);
  }
/**
 * Updates an ingredient.
 * @param ingredient A selfmade data transfer object.
 * @param ingredientId Any number.
 * @returns A confirmation if the update was successful.
 */
  async updateIngredient(ingredient: CreateIngredientRequestDto, ingredientId: number): Promise<boolean> {
    const json = JSON.stringify(ingredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients${ingredientId}`, {
      method: "PATCH",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return await this.returnIfSuccessElseError(result, true);
  }
/**
 * Gives the rating for any recipe given by any user.
 * @param userId Any number.
 * @param recipeId Any number.
 * @returns The corresponding rating matching the userId and recipeId
 */
  async getRating(userId: number, recipeId: number): Promise<number> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/rating/${userId}/${recipeId}`, {
      headers: this.DEFAULT_HEADER,
    });
    const text = await result.text();
    return await this.returnIfSuccessElseError(result, parseInt(text));
  }

  /**
 * Gives a specified number of recipes.
 * @param limit Any number.
 * @returns A list of recipes with the amount determined by the limit.
 */
  async getRecipes(limit?: number, sortBy: RecipeFilter = RecipeFilter.NEWEST): Promise<RecipeResponseDto[]> {
    let url = this.BASE_URL + "/api/recipes?sort_by=" + sortBy;

    const result = await fetch(url, {
      headers: this.setHeader(),
    });
    const recipes: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipes);
  }
/**
 * Searches for a specific recipe by id.
 * @param id Any number.
 * @returns The recipe with its corresponding attributes matching the id.
 */
//TODO param test wegmachen!
  async getRecipe(id: String, test: String = "nicht"): Promise<RecipeResponseDto> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${id}`, {
      headers: this.setHeader(),
    });
    const recipe: RecipeResponseDto = await result.json();
    return await this.returnIfSuccessElseError(result, recipe);
  }

  async getRecipesOfUser(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/myrecipes`, {
      headers: this.setHeader(),
    });
    const recipes: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipes);
  }

/**
 * Creates a new recipe.
 * @param recipe A selfmade data transfer object.
 */
  async createRecipe(recipe: CreateRecipeRequestDto): Promise<void> {
    const json = JSON.stringify(recipe);
    console.log("in create recipe " + getCookie("token"));
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      method: "POST",
      body: json,
    });
    await this.returnIfSuccessElseError(result, true);
  }
/**
 * Creates a new recipe with an image.
 * @param recipe A selfmade data transfer object.
 * @param image Any File.
 */
  async createRecipeWithImage(recipe: CreateRecipeRequestDto, image: File): Promise<void> {
    const json = JSON.stringify(recipe);
    const formData = new FormData();
    formData.append("file", image);
    formData.append(
      "recipe",
      new Blob([json], {
        type: "application/json",
      })
    );
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      method: "POST",
      body: formData,
      headers: {
        Authorization: `Bearer ${getCookie("token")}`,
      },
    });
    await this.returnIfSuccessElseError(result, true);
  }

  /**
 * Updates a recipe.
 * @param recipe A selfmade data transfer object.
 * @param recipeId Any number.
 */
  async updateRecipe(recipe: CreateRecipeRequestDto, recipeId: number, image: File): Promise<void> {
    const json = JSON.stringify(recipe);
    const formData = new FormData();

    formData.append(
      "recipe",
      new Blob([json], {
        type: "application/json",
      })
    );
    formData.append("file", image);
    const result = await fetch(`${this.BASE_URL}/api/recipes/${recipeId}`, {
      method: "PATCH",
      body: formData,
      headers: this.setHeader(),
    });
    await this.returnIfSuccessElseError(result, true);
  }
/**
 * Deletes a recipe.
 * @param recipeId Any number.
 */
  async deleteRecipe(recipeId: number): Promise<void> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${recipeId}`, {
      method: "DELETE",
      headers: this.setHeader(),
    });
    await this.returnIfSuccessElseError(result, true);
  }
/**
 * Adds a recipe to the 'Favorites' tab.
 * @param recipeId Any number.
 */
  async favoriteRecipe(recipeId: number): Promise<void> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/favorites/${recipeId}`, {
      method: "POST",
      headers: this.setHeader(),
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async unfavoriteRecipe(recipeId: number): Promise<void> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/favorites/unfavorite/${recipeId}`, {
      method: "POST",
      headers: this.setHeader(),
    });
    await this.returnIfSuccessElseError(result, true);
  }
/**
 * Gives all recipes from the 'Favorites' tab.
 * @returns A list off all recipes added to the 'Favorites' tab.
 */
  async getFavoriteRecipes(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/favorites/ofUser`, {
      headers: this.setHeader(),
    });
    const recipe: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipe);
  }
/**
 * Updates a profile with an image.
 * @param user A selfmade data transfer object.
 * @param image Any file.
 */
  async updateUserWithImage(user: UpdateUserRequestDto, image: File): Promise<void> {
    const json = JSON.stringify(user);
    const formData = new FormData();
    formData.append("file", image);
    formData.append(
      "user",
      new Blob([json], {
        type: "application/json",
      })
    );
    const result = await fetch(`${this.BASE_URL}/api/users`, {
      method: "PATCH",
      body: formData,
      headers: {
        Authorization: `Bearer ${getCookie("token")}`,
      },
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async getUser(): Promise<UserDto> {
    const result = await fetch(`${this.BASE_URL}/api/users`, {
      headers: this.setHeader(),
    });
    const user: UserDto = await result.json();
    return await this.returnIfSuccessElseError(result, user);
  }

/**
 * Registers a user allowing them to log in.
 * @param registerDto A selfmade data transfer object.
 * @returns A confirmation if the registration was successful.
 */
  async register(registerDto: RegisterRequestDto): Promise<boolean> {
    const json = JSON.stringify(registerDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/register`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return this.returnIfSuccessElseError(result, true);
  }
/**
 * Logs a user in.
 * @param loginDto A selfmade data transfer object. 
 * @returns A JSON Web Token.
 */
  async login(loginDto: LoginRequestDto): Promise<any> {
    const json = JSON.stringify(loginDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/login`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    const responseBody = await result.json();

    Cookies.set("token", responseBody.token);

    return this.returnIfSuccessElseError(result, responseBody);
  }
/**
 * Logs a user out.
 * @returns A confirmation if the logout was successful.
 */
  async logout(): Promise<boolean> {
    const result = await fetch(`${this.BASE_URL}/api/auth/signout`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
    });
    Cookies.remove("token");
    return this.returnIfSuccessElseError(result, true);
  }
/**
 * Checks if any request was successful.
 * @param response A response to a API-request.
 * @param success A simple parameter to let you know if everything worked as intended.
 * @returns A confirmation if the request was successful or throws an error if it was not. 
 */
  private async returnIfSuccessElseError<T>(response: Response, success: T): Promise<T> {
    let error = "";
    console.log(response.status);
    if (response.ok) {
      return success;
    }
    if (response.bodyUsed) {
      response.body;
    } else {
      error = await response.json();
      error = JSON.stringify(error);
    }
    throw new StudentRecipesClientError(error);
  }
/**
 * Sets the header, reads the cookie and authorizes the user.
 * @returns The JSON Web Token which is used for authorization.
 */
  private setHeader(): any {
    return {
      "Content-Type": "application/json",
      Authorization: `Bearer ${getCookie("token")}`,
    };
  }
}
/**
 * This class helps with manual error handling.
 */
export class StudentRecipesClientError extends Error {
  constructor(readonly message: string) {
    super(message);
  }
}