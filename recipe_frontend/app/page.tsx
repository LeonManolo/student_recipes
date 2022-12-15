"use client";
import Cookies from "js-cookie";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import Login_Register from "../components/Login_Register";
import RecipeOverview from "../components/RecipeOverview";
import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";

export default function HomePage() {
  const [recipes, setRecipes] = useState<RecipeResponseDto[]>([]);

  useEffect(() => {
    fetchRecipes();
  }, []);

  const router = useRouter();
  return (
    <div>
    <div className="hero min-h-screen bg-base-200" style={{ backgroundImage: `url("/cooking.jpg")` }}>
      <div className="hero-overlay bg-opacity-60"></div>
      <div className="hero-content flex flex-row gap-x-32 lg:flex-row-reverse ">
        <Login_Register />
        <div className="text-center text-white">
          <h1 className="text-5xl font-bold ">Studentenkueche</h1>
          <p className="py-6">Leckere Rezepte für leckere Studenten</p>
        </div>
              
      </div>
    </div>
    <div className="flex justify-center">
        {recipes.map((recipe, index) => (
          <div key={index} className='flex p-4 hover:scale-105 transition-all duration-500 cursor-pointer'>
            <Link href={`recipes/${recipe.id}`} ><RecipeOverview recipe={recipe} /> </Link>
          </div>
        ))}
      </div>
      </div>
  );

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
