import Cookies from "js-cookie";
import CreateIngredientRequestDto from "./dto/CreateIngredientRequestDto";
import CreateRecipeRequestDto from "./dto/CreateRecipeRequestDto";
import IngredientDto from "./dto/IngredientDto";
import LoginRequestDto from "./dto/LoginRequestDto";
import RecipeResponseDto from "./dto/RecipeResponseDto";
import RegisterRequestDto from "./dto/RegisterRequestDto";
import { getCookie } from "cookies-next";
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

  async getIngredient(id: number): Promise<IngredientDto> {
    const result = await fetch(`${this.BASE_URL}/api/ingredients${id}`, {
      headers: this.DEFAULT_HEADER,
    });
    const ingredient: IngredientDto = await result.json();
    return await this.returnIfSuccessElseError(result, ingredient);
  }

  async createIngredient(ingredient: CreateIngredientRequestDto): Promise<void> {
    const json = JSON.stringify(ingredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async updateIngredient(ingredient: CreateIngredientRequestDto, ingredientId: number): Promise<boolean> {
    const json = JSON.stringify(ingredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients${ingredientId}`, {
      method: "PATCH",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return await this.returnIfSuccessElseError(result, true);
  }

  async getRating(userId: number, recipeId: number): Promise<number> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/rating/${userId}/${recipeId}`, {
      headers: this.DEFAULT_HEADER,
    });
    const text = await result.text();
    return await this.returnIfSuccessElseError(result, parseInt(text));
  }

  async getRecipes(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      headers: this.DEFAULT_HEADER,
    });
    const recipes: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipes);
  }

  async getRecipe(id: String, test: String = "nicht"): Promise<RecipeResponseDto> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${id}`, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${getCookie("token")}`,
      },
    });
    const recipe: RecipeResponseDto = await result.json();
    return await this.returnIfSuccessElseError(result, recipe);
  }

  async createRecipe(recipe: CreateRecipeRequestDto): Promise<void> {
    const json = JSON.stringify(recipe);
    console.log("in create recipe " + getCookie("token"));
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      method: "POST",
      body: json,
    });
    await this.returnIfSuccessElseError(result, true);
  }

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

  async updateRecipe(recipe: CreateRecipeRequestDto, recipeId: number): Promise<void> {
    const json = JSON.stringify(recipe);
    const result = await fetch(`${this.BASE_URL}/api/recipes${recipeId}`, {
      method: "PATCH",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async deleteRecipe(recipeId: number): Promise<void> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${recipeId}`, {
      method: "DELETE",
      headers: this.DEFAULT_HEADER,
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async favoriteRecipe(recipeId: number): Promise<void> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/favorites/${recipeId}`, {
      method: "POST",
      headers: this.setHeader(),
    });
    await this.returnIfSuccessElseError(result, true);
  }

  async unfavoriteRecipe(recipeId: number): Promise<void> {
    // TODO
  }

  async getFavoriteRecipes(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/ofUser`, {
      headers: this.setHeader(),
    });
    const recipe: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipe);
  }

  async register(registerDto: RegisterRequestDto): Promise<boolean> {
    const json = JSON.stringify(registerDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/register`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return this.returnIfSuccessElseError(result, true);
  }

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

  async logout(): Promise<boolean> {
    const result = await fetch(`${this.BASE_URL}/api/auth/signout`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
    });
    return this.returnIfSuccessElseError(result, true);
  }

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

  private setHeader(): any {
    return {
      "Content-Type": "application/json",
      Authorization: `Bearer ${getCookie("token")}`,
    };
  }
}

export class StudentRecipesClientError extends Error {
  constructor(readonly message: string) {
    super(message);
  }
}
