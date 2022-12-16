"use client";
import Cookies from "js-cookie";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import Login_Register from "../components/Login_Register";
import RecipeOverview from "../components/RecipeOverview";
import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";

/* Main page with login/register option and overview of 3 recipes. */
export default function HomePage() {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);

  /* State hook calls fetRecipes() only when component is initially rendered. */
  useEffect(() => {
    fetchRecipes();
  }, []);

  const router = useRouter();
  return (
    <div>
      <div className="hero min-h-screen bg-base-200" style={{ backgroundImage: `url("/cooking.jpg")` }}>
        <div className="hero-overlay bg-opacity-60"></div>
        <div className="hero-content flex flex-row justify-center ">
          <div className="text-center text-white">
            <h1 className="text-5xl font-bold ">Studentenkueche</h1>
            <p className="py-6">Leckere Rezepte für leckere Studenten</p>
          </div>
          {/* Component where user can switch between login and register. */}
          <Login_Register />
        </div>
      </div>
      <div className="flex justify-center">
        {/* Loop through the 3 provided recipes. */}
        {recipes.map((recipe, index) => (
          <div key={index} className="flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer">
            <Link href={`recipes/${recipe.id}`}>
              <RecipeOverview recipe={recipe} />{" "}
            </Link>
          </div>
        ))}
      </div>
    </div>
  );

  /* Calls endpoint to get recipes with optional parameter of 3 to only fetch 3 recipes */
  async function fetchRecipes(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    try {
      const fetchedRecipes = await recipeClient.getRecipes(3);
      setRecipes(fetchedRecipes);
    } catch (e: any) {
      alert(e?.message ?? e);
    }
  }
}
