"use client";
import RecipeOverview from "../../components/RecipeOverview";
import Tab from "../../components/Tab";
import Link from "next/link";
import StudentRecipesClient from "../../utils/StudentRecipesClient";
import RecipeResponseDto from "../../utils/dto/RecipeResponseDto";
import EditRecipeOverview from "../../components/EditRecipeOverview";
import { useState, useEffect } from "react";

/* Page to give an overview of all recipes created by user. */
export default function MyRecipes({ params }: any) {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);

  /* State hook calls fetRecipes() only when component is initially rendered. */
  useEffect(() => {
    fetchRecipes();
  }, []);
  const id = params.id;

  return (
    <div>
      <div className="flex flex-row">
        <div className="flex flex-row flex-wrap w-full">
          <h1 className="w-full mx-4">Meine Rezepte</h1>
          <div className="flex flex-row flex-wrap w-full">
            {/* Dynamically loops recipe overview containg option to edit and delete recipe. */}
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
  
  /* Fetches all recipes from endpoint. */
  async function fetchRecipes(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    const recipesFetched = await recipeClient.getRecipesOfUser();
    setRecipes(recipesFetched);
  }
}
