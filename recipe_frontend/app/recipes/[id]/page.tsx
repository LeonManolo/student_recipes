"use client";
import RecipeOverview from "../../../components/RecipeOverview";
import RecipeTable from "../../../components/RecipeTable";
import Image from "next/image";
import img from "../../../public/food/pizza3.png";
import { FiClock } from "react-icons/fi";
import { FiThumbsUp } from "react-icons/fi";
import { HiFire, HiOutlineChartBar, HiOutlineCurrencyEuro } from "react-icons/hi";
import RecipeResponseDto from "../../../utils/dto/RecipeResponseDto";
import StudentRecipesClient from "../../../utils/StudentRecipesClient";
import NutritionTable from "../../../components/NutritonTable";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import Rating from "../../../components/Rating";
import { notFound } from "next/navigation";
import Cookies from "js-cookie";
import { cookies } from "next/headers";
import { getCookie, setCookie } from "cookies-next";
import { useEffect, useState } from "react";

export default function RecipeDetail({ params }: any) {
  const [recipe, setRecipe] = useState<RecipeResponseDto>();
  const [liked, setLiked] = useState(false);
  //const recipe = (await fetchRecipe(params.id))!!;
  //const rating = await fetchRating(recipe.author.id, params.id);

  useEffect(() => {
    fetchRecipe();
  }, []);

  if (recipe === undefined) return <>Loading</>;

  return (
    <div className="flex justify-center mt-4">
      <div className="card shadow-xl">
        <div className="card-body flex-row justify-center">
          <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
            <Image className="rounded-t-xl " src={img} alt="recipe" width={500} height={500} />
            <div className="card-body">
              <div className="flex flex-col">
                <div className="flex flex-row flex-wrap">
                  <h1 className="card-title text-3xl">{recipe.title}</h1>
                </div>
                {/*<Rating rating={rating}></Rating>*/}
              </div>
              <h3 className="text-slate-500">{recipe.description}</h3>
              <div className="flex flex-row">
                <HiFire size="20" className="mr-1"></HiFire>
                <p>{recipe.totalCalories}</p>
                <FiClock size="20" className="mr-1"></FiClock>
                <p>{recipe.cookTime}</p>
                <HiOutlineCurrencyEuro size="20" className="mr-1"></HiOutlineCurrencyEuro>
                <p>{recipe.price}</p>
                <HiOutlineChartBar size="20" className="mr-1"></HiOutlineChartBar>
                <p>{recipe.views}</p>
                <HiOutlineFaceSmile size="20" className="mr-1"></HiOutlineFaceSmile>
                <p>{recipe.author.firstName}</p>
              </div>
            </div>
          </div>
          <div className="w-1/2">
            <RecipeTable recipe={recipe} />
          </div>
          <NutritionTable recipe={recipe} />
        </div>
      </div>
    </div>
  );
  async function fetchRecipe(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    try {
      const recipeFetched = await recipeClient.getRecipe(params.id, Cookies.get("token"));
      //const ratingFetched = await recipeClient.getRating(1, params.id); // TODO: HARDCODE ÄNDERN°
      setRecipe(recipeFetched);
    } catch (e: any) {
      console.log("IM ERROR!");
      console.log(e.message);
      console.log(e.message);
    }
  }

  async function fetchRating(userId: number, recipeId: number): Promise<number> {
    const recipeClient = new StudentRecipesClient();
    const rating = await recipeClient.getRating(userId, recipeId);
    return rating;
  }
}
