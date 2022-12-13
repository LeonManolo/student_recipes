import Filter from '../../components/Filter';
import RecipeOverview from '../../components/RecipeOverview';
import Tab from '../../components/Tab';
import Link from "next/link";
import StudentRecipesClient from '../../utils/StudentRecipesClient';
import RecipeResponseDto from '../../utils/dto/RecipeResponseDto';

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function Favorites({ params }: any) {
    const recipes = await fetchRecipes(params.id);
    return (
        <div >
            <div className='flex flex-row'>
                <div className='flex flex-row flex-wrap w-full'>
                    <h1 className='w-full mx-4'>Meine Favoriten</h1>
                    <div className='flex flex-row-reverse flex-wrap w-full'>
                        {recipes.map((recipe, index) => (
                            <div key={index} className='flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer'>
                                <Link href={`recipes/${recipe.id}`} ><RecipeOverview recipe={recipe} /> </Link>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

async function fetchRecipes(userId: number): Promise<RecipeResponseDto[]> {
    const recipeClient = new StudentRecipesClient()
    const recipes = recipeClient.getFavorites(userId)
    return recipes;
}