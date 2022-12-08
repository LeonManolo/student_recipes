import DropDown from '../../components/DropDown';
import RecipeOverview from '../../components/RecipeOverview';
import Tab from '../../components/Tab';
import Link from "next/link";

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function Recipes({ params }: any) {
  const recipes = await fetchRecipes();
  const id = params.id;
  return (
    <div >
      <div className='flex flex-row'>
        <div className='card shadow-xlw-1/5 h-1/3 bg-base-100 ml-4 mt-10'>
          <div className='card-body'>
            <h2 className='text-xl'>Rezepte filtern</h2>
            <div className='flex'><Tab /></div>
            <div className='flex pt-4'><DropDown /></div>
          </div>
        </div>
        <div className='flex flex-row-reverse flex-wrap w-3/4'>
          {recipes.map((element, index) => (
            <div key={index} className='flex p-4'>
              <Link href="recipes/{id}" ><RecipeOverview /> </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

async function fetchRecipes(): Promise<any[]> {
  const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
  const recipes = await result?.json();
  return recipes.products; // für die dummmy api musste ich products aufrufen
}
