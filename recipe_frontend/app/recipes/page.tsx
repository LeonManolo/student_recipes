import Filter from '../../components/Filter';
import RecipeOverview from '../../components/RecipeOverview';
import Tab from '../../components/Tab';
import Link from "next/link";
import StudentRecipesClient from '../../utils/StudentRecipesClient';
import RecipeResponseDto from '../../utils/dto/RecipeResponseDto';

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function Recipes({ params }: any) {
  const recipes = await fetchRecipes();
  const id = params.id;
  return (
    <div >
      <div className='flex flex-row'>
        <div className='card shadow-xl h-1/3 bg-base-100 ml-8'>
          <div className='card-body'>
            <h2>Filter</h2>
            <div className='flex'><Tab /></div>
            <h2>Kategorie</h2>
            <div className='flex'><Filter /></div>
          </div>
        </div>
        <div className='flex flex-row-reverse flex-wrap w-full'>
          {recipes.map((recipe, index) => (
            <div key={index} className='flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer'>
              {recipe.title}
              {recipe.description}
              <Link href="recipes/{id}" ><RecipeOverview recipe={recipe} /> </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

async function fetchRecipes(): Promise<RecipeResponseDto[]> {
  /*
  const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
  const recipes = await result?.json();
  return recipes.products; // für die dummmy api musste ich products aufrufen
  */

  const recipeClient = new StudentRecipesClient()
  const recipes = recipeClient.getRecipes()
  return recipes;
}
