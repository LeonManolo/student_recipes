import RecipeOverview from '../../components/RecipeOverview';
import Tab from '../../components/Tab';
import Link from "next/link";
import StudentRecipesClient from '../../utils/StudentRecipesClient';
import RecipeResponseDto from '../../utils/dto/RecipeResponseDto';

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function MyRecipes({ params }: any) {
    const recipes = await fetchRecipes();
    const id = params.id;
    return (
        <div >
            <div className='flex flex-row'>
                <div className='flex flex-row flex-wrap w-full'>
                    <h1 className='w-full mx-4'>Meine Rezepte</h1>
                    <div className='flex flex-row-reverse flex-wrap w-full'>
                        {recipes.map((recipe, index) => (
                            <div key={index} className='flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer'>
                                {recipe.title}
                                {recipe.description}
                                <Link href="recipes/{id}" ><RecipeOverview recipe={recipe} /> </Link>
                            </div>
                        ))}
                    </div>
                    <div className="alert alert-error shadow-lg">
                        <div>
                            <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
                            <span>Error! Task failed successfully.</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

async function fetchRecipes(): Promise<RecipeResponseDto[]> {
    const recipeClient = new StudentRecipesClient()
    const recipes = recipeClient.getRecipes()
    return recipes;
}

/* async function fetchRecipes(): Promise<any[]> {
    const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
    const recipes = await result?.json();
    return recipes.products; // für die dummmy api musste ich products aufrufen
}
 */