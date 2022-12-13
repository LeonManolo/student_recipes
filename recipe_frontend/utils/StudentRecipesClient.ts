import CreateIngredientRequestDto from "./dto/CreateIngredientRequestDto";
import CreateRecipeRequestDto from "./dto/CreateRecipeRequestDto";
import IngredientDto from "./dto/IngredientDto";
import LoginRequestDto from "./dto/LoginRequestDto";
import RecipeResponseDto from "./dto/RecipeResponseDto";
import RegisterRequestDto from "./dto/RegisterRequestDto";

/**
 * This class contains all functions that are necessary for all of the
 * API requests. To do this, the functions use the associated dto's.
 */
export default class StudentRecipesClient {
  private readonly BASE_URL = "https://sea-turtle-app-hqisk.ondigitalocean.app";
  private readonly DEFAULT_HEADER = {
    "Content-Type": "application/json",
  };

  async getIngredients(): Promise<IngredientDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/ingredients`, {
      headers: this.DEFAULT_HEADER,
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

  async getRecipes(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      headers: this.DEFAULT_HEADER,
    });
    const recipes: RecipeResponseDto[] = await result.json();
    return await this.returnIfSuccessElseError(result, recipes);
  }

  async getRecipe(id: String): Promise<RecipeResponseDto> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${id}`, {
      headers: this.DEFAULT_HEADER,
    });
    const recipe: RecipeResponseDto = await result.json();
    return await this.returnIfSuccessElseError(result, recipe);
  }

  async createRecipe(recipe: CreateRecipeRequestDto): Promise<void> {
    const json = JSON.stringify(recipe);
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
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
    const result = await fetch(`http://localhost:8080/api/recipes`, {
      method: "POST",
      body: formData,
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

  async register(registerDto: RegisterRequestDto): Promise<boolean> {
    const json = JSON.stringify(registerDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/register`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return this.returnIfSuccessElseError(result, true);
  }

  async login(loginDto: LoginRequestDto): Promise<boolean> {
    const json = JSON.stringify(loginDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/login`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return this.returnIfSuccessElseError(result, true);
  }

  async logout(): Promise<boolean> {
    const result = await fetch(`${this.BASE_URL}/api/auth/signout`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
    });
    return this.returnIfSuccessElseError(result, true);
  }

  private async returnIfSuccessElseError<T>(response: Response, success: T): Promise<T> {
    if (response.ok) {
      return success;
    }
    const error = await response.text();
    throw new StudentRecipesClientError(error);
  }
}

export class StudentRecipesClientError extends Error {
  constructor(readonly message: string) {
    super(message);
  }
}
