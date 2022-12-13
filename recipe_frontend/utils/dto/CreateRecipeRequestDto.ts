export default interface CreateRecipeRequestDto {
  title: string;
  description: string;
  cookTime: number;
  servings: number;
  authorId: number; //TODO: nur temporär
  steps: RecipeStepDto[];
}

export interface RecipeStepDto {
  title: string;
  description: string;
  ingredients: IngredientInfoDto[];
}

export interface IngredientInfoDto {
  ingredientId: number;
  title: string; // Nur für state im Backend nicht benutzt
  amount: number;
  unit: string; // einheit e.g. "g" oder "ml"
}

