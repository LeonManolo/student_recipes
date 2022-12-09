import RecipeOverview from '../../components/RecipeOverview';
import Tab from '../../components/Tab';
import Link from "next/link";

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function MyRecipes({ params }: any) {
    const recipes = await fetchRecipes();
    const id = params.id;
    return (
        <div >
            <div className='flex flex-row'>
                <div className='flex flex-row flex-wrap w-full'>
                    <h1 className='w-full mx-4'>Meine Rezepte</h1>
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
