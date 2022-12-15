"use client";
import RecipeOverview from "../../components/RecipeOverview";
import Tab from "../../components/Tab";
import Link from "next/link";
import StudentRecipesClient from "../../utils/StudentRecipesClient";
import RecipeResponseDto from "../../utils/dto/RecipeResponseDto";
import EditRecipeOverview from "../../components/EditRecipeOverview";
import { useState, useEffect } from "react";

// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default function MyRecipes({ params }: any) {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);

  useEffect(() => {
    fetchRecipes();
  }, []);
  const id = params.id;

  return (
    <div>
      <div className="flex flex-row">
        <div className="flex flex-row flex-wrap w-full">
          <h1 className="w-full mx-4">Meine Rezepte</h1>
          <div className="flex flex-row-reverse flex-wrap w-full">
            {recipes.map((recipe, index) => (
              <div key={index} className="flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer">
                <Link href={`recipes/${recipe.id}`}>
                  <EditRecipeOverview recipe={recipe} />{" "}
                </Link>
              </div>
            ))}
          </div>
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
