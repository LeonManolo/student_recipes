import CreateIngredientRequestDto from "./dto/CreateIngredientRequestDto";
import CreateRecipeRequestDto from "./dto/CreateRecipeRequestDto";
import IngredientDto from "./dto/IngredientDto";
import LoginRequestDto from "./dto/LoginRequestDto";
import RecipeResponseDto from "./dto/RecipeResponseDto";
import RegisterRequestDto from "./dto/RegisterRequestDto";

export default class StudentRecipesClient {
  private readonly BASE_URL = "https://sea-turtle-app-hqisk.ondigitalocean.app";
  private readonly DEFAULT_HEADER = {
    "Content-Type": "application/json",
  };

  async getIngredients(): Promise<IngredientDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/ingredients`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
    });
    const ingredients: IngredientDto[] = await result.json();
    return ingredients;
  }

  async getIngredient(id: number): Promise<IngredientDto> {
    const result = await fetch(`${this.BASE_URL}/api/ingredients${id}`, {
      headers: this.DEFAULT_HEADER,
    });
    const ingredient: IngredientDto = await result.json();
    return ingredient;
  }

  async createIngredient(ingredient: CreateIngredientRequestDto): Promise<boolean> {
    const json = JSON.stringify(ingredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async updateIngredient(ingredient: CreateIngredientRequestDto, ingredientId: number): Promise<boolean> {
    const json = JSON.stringify(ingredient);
    const result = await fetch(`${this.BASE_URL}/api/ingredients${ingredientId}`, {
      method: "PATCH",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async getRecipes(): Promise<RecipeResponseDto[]> {
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      headers: this.DEFAULT_HEADER,
    });
    const recipes: RecipeResponseDto[] = await result.json();
    return recipes;
  }

  async getRecipe(id: number): Promise<RecipeResponseDto> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${id}`, {
      headers: this.DEFAULT_HEADER,
    });
    const recipe: RecipeResponseDto = await result.json();
    return recipe;
  }

  async createRecipe(recipe: CreateRecipeRequestDto): Promise<boolean> {
    const json = JSON.stringify(recipe);
    const result = await fetch(`${this.BASE_URL}/api/recipes`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async updateRecipe(recipe: CreateRecipeRequestDto, recipeId: number): Promise<boolean> {
    const json = JSON.stringify(recipe);
    const result = await fetch(`${this.BASE_URL}/api/recipes${recipeId}`, {
      method: "PATCH",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async deleteRecipe(recipeId: number): Promise<boolean> {
    const result = await fetch(`${this.BASE_URL}/api/recipes/${recipeId}`, {
      method: "DELETE",
      headers: this.DEFAULT_HEADER,
    });
    return result.ok;
  }

  async register(registerDto: RegisterRequestDto): Promise<boolean> {
    const json = JSON.stringify(registerDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/register`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async login(loginDto: LoginRequestDto): Promise<boolean> {
    const json = JSON.stringify(loginDto);
    const result = await fetch(`${this.BASE_URL}/api/auth/login`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
      body: json,
    });
    return result.ok;
  }

  async logout(): Promise<boolean> {
    const result = await fetch(`${this.BASE_URL}/api/auth/signout`, {
      method: "POST",
      headers: this.DEFAULT_HEADER,
    });
    return result.ok;
  }
}
