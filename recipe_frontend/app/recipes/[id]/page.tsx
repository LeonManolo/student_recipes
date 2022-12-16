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
import { notFound } from "next/navigation";
import Cookies from "js-cookie";
import { cookies } from "next/headers";
import { getCookie, setCookie } from "cookies-next";
import { useEffect, useState } from "react";
import { Rating } from "@mui/material";
import { BsHeart, BsHeartFill } from "react-icons/bs";

/* Page to show all detailed information of a single recipe. 
  @params params Contains a Recipe. */

export default function RecipeDetail({ params }: any) {
  const [recipe, setRecipe] = useState<RecipeResponseDto>();
  const [rating, setRating] = useState(0);

  /* State hook to set current recipe. */
  useEffect(() => {
    fetchRecipe();
  }, []);

  if (recipe === undefined) return <>Loading</>;

  return (
    <div className="flex justify-center mt-4">
      <div className="card shadow-xl">
        <div className="card-body flex-row justify-center">
          <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
            <Image className="rounded-t-xl " src={recipe.imageUrl!!} alt="recipe" width={500} height={500} />
            <div className="card-body">
              <div className="flex flex-col">
                <div className="flex flex-row flex-wrap">
                  <h1 className="card-title text-3xl">{recipe.title}</h1>
                </div>
                {/*<Rating rating={rating}></Rating>*/}
              </div>
              <Rating
                readOnly={false}
                name="size-medium"
                defaultValue={recipe.averageRating}
                value={rating}
                icon={<BsHeartFill className="mr-1" color="#C98474" />}
                emptyIcon={<BsHeart className="mr-1" color="#C98474" />}
                onChange={(e, v) => v && onRatingChange(v)}
              />
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
            {/* Table of all necessary steps to cook recipe. */}

            <RecipeTable recipe={recipe} />
          </div>
          {/* Table of recipes nutrional value e.g. proteins, carbohydrates, fats etc. */}
          <NutritionTable recipe={recipe} />
        </div>
      </div>
    </div>
  );

  /* Fetches a single recipe by id. */
  async function fetchRecipe(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    try {
      const recipeFetched = await recipeClient.getRecipe(params.id, Cookies.get("token"));
      //const ratingFetched = await recipeClient.getRating(1, params.id); // TODO: HARDCODE ÄNDERN°
      setRecipe(recipeFetched);
      fetchRating(recipeFetched.id);
    } catch (e: any) {
      console.log("IM ERROR!");
      console.log(e.message);
    }
  }

  async function fetchRating(recipeId: number): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    try {
      const fetchedRating = await recipeClient.getRating(recipeId);
      setRating(fetchedRating);
    } catch (e) {
      console.log(e);
    }
  }

  /* Fetches the rating from 1 to 5 of a recipe by recipe id and user id. */
  async function onRatingChange(rating: number): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    await recipeClient.createRating(recipe?.id!!, rating);
    setRating(rating);
  }
}
