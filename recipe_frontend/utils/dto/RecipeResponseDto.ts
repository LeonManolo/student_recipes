import IngredientDto from "./IngredientDto";
import UserDto from "./UserDto";

export default interface RecipeResponseDto {
    id: number;
    title: string;
    description: string;
    servings: number;
    author: UserDto; // Der Author
    price: number;
    averageRating: number;
    cookTime: number;
    imageUrl?: string;
    views: number;
    totalCalories: number;
    totalProtein: number;
    totalCarbohydrates: number;
    totalFat: number;
    ingredients: TotalIngredient[];
    steps: RecipeStep[];

}

interface RecipeStep {
    title: string;
    description: string;
    stepNumber: number;
    imageUrl?: string;
    ingredients: RecipeStepIngredientInfo[]
}

interface RecipeStepIngredientInfo {
    amount: number;
    unit: string;
    ingredient: IngredientDto;
}

interface TotalIngredient {
    id: number;
    title: string;
    calories: number;
    protein: number;
    fat: number;
    carbohydrates: number;
    imageUrl?: string;
    unit: string;
}