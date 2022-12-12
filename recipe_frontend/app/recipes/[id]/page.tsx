import RecipeOverview from '../../../components/RecipeOverview';
import RecipeTable from '../../../components/RecipeTable';
import Image from 'next/image';
import img from '../../../public/food/pizza3.png'
import { FiClock } from "react-icons/fi";
import { FiThumbsUp } from "react-icons/fi";
import { HiFire } from "react-icons/hi";
import RecipeResponseDto from '../../../utils/dto/RecipeResponseDto';
import StudentRecipesClient from '../../../utils/StudentRecipesClient';
import NutritionTable from '../../../components/NutritonTable';
export default async function RecipeDetail({ params }: any) {
  const recipe = await fetchRecipe(params.id);

  return (
    <div className='flex justify-center mt-4'>
      <div className='card shadow-xl'>
        <NutritionTable recipe={recipe} />
        <div className='card-body flex-row justify-center'>
          <RecipeOverview recipe={recipe} />
          <div className='w-1/2'>
            <RecipeTable recipe={recipe} />
          </div>
        </div>
      </div>
    </div>
  );
  async function fetchRecipe(id: String): Promise<RecipeResponseDto> {
    const recipeClient = new StudentRecipesClient()
    const recipe = await recipeClient.getRecipe(id)
    return recipe;
  }
}