"use client";
import Filter from "../../components/Filter";
import RecipeOverview from "../../components/RecipeOverview";
import Tab from "../../components/Tab";
import Link from "next/link";
import StudentRecipesClient from "../../utils/StudentRecipesClient";
import RecipeResponseDto from "../../utils/dto/RecipeResponseDto";
import { useEffect, useState } from "react";
import { RecipeFilter } from "../../utils/dto/RecipeFilter";

/* Page to show all recipes on the site. */
export default function Recipes() {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);
  let filter = RecipeFilter.NEWEST;

  /* State hook calls fetRecipes() only when component is initially rendered. */
  useEffect(() => {
    fetchRecipes();
  }, []);

  return (
    <div>
      <div className="flex flex-row">
        <div className="card shadow-xl h-1/3 bg-base-100 ml-8">
          <div className="card-body">
            <h2>Filter</h2>
            <div className="flex">
              <Tab onTabChange={(f) => fetchRecipes(f)} />
            </div>
            <h2>Kategorie</h2>
            <div className="flex">
              <Filter />
            </div>
          </div>
        </div>
        <div className="flex flex-row flex-wrap w-full">
          {recipes.map((recipe, index) => (
            <div key={index} className="flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer">
              <Link href={`recipes/${recipe.id}`}>
                {/* Provides every recipe as overview component in a loop. */}
                {/* Dynamically links to detail page of recipe when clicked. */}
                <RecipeOverview recipe={recipe} />{" "}
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );

  /* Fetches all recipes from endpoint. */
  async function fetchRecipes(filter: RecipeFilter = RecipeFilter.NEWEST): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    const recipesFetched = await recipeClient.getRecipes(1000, filter);
    setRecipes(recipesFetched);
  }
}

/* async function fetchRecipes(): Promise<any[]> {
    const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
    const recipes = await result?.json();
    return recipes.products; // für die dummmy api musste ich products aufrufen
}
 */
