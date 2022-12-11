export default interface CreateRecipeRequestDto {
  title: string;
  description: string;
  servings: number;
  authorId: number; //TODO: nur temporär
  steps: RecipeStepDto[];
}

interface RecipeStepDto {
  title: string;
  description: string;
  ingredients: IngredientInfoDto[];
}

interface IngredientInfoDto {
  ingredientId: number;
  amount: number;
  unit: string; // einheit e.g. "g" oder "ml"
}

