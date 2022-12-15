"use client";
import Filter from "../../components/Filter";
import RecipeOverview from "../../components/RecipeOverview";
import Tab from "../../components/Tab";
import Link from "next/link";
import StudentRecipesClient from "../../utils/StudentRecipesClient";
import RecipeResponseDto from "../../utils/dto/RecipeResponseDto";
import { useEffect, useState } from "react";

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default function Recipes() {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);

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
              <Tab />
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
                <RecipeOverview recipe={recipe} />{" "}
              </Link>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
  async function fetchRecipes(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    const recipesFetched = await recipeClient.getRecipes();
    setRecipes(recipesFetched);
  }
}

/* async function fetchRecipes(): Promise<any[]> {
    const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
    const recipes = await result?.json();
    return recipes.products; // für die dummmy api musste ich products aufrufen
}
 */
