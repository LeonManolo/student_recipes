import img from "../public/food/pizza3.png";
import Image from "next/image";
import { FiClock } from "react-icons/fi";
import { HiOutlineChartBar } from "react-icons/hi2";
import { HiFire } from "react-icons/hi";
import { HiOutlineStar } from "react-icons/hi";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import React from "react";
import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import { HiOutlineCurrencyEuro } from "react-icons/hi2";
import { Rating } from "@mui/material";
import { BsHeart, BsHeartFill } from "react-icons/bs";

/* Component that renders an overview of most relevant data of a recipe.
    Used in most pages where an overview of a recipe is needed. 
    @param recipe Provides all data of a recipe.*/
export default function RecipeOverview({ recipe }: { recipe: RecipeResponseDto }) {
  return (
    <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
      <Image className="rounded-t-xl " src={recipe.imageUrl!!} alt="recipe" width={500} height={500} />
      <div className="card-body">
        <div className="flex flex-col">
          <div className="flex flex-row flex-wrap">
            <h1 className="card-title text-3xl">{recipe.title}</h1>
          </div>
          <Rating
            readOnly={true}
            name="size-medium"
            defaultValue={recipe.averageRating}
            icon={<BsHeartFill className="mr-1" color="#570df8" />}
            emptyIcon={<BsHeart className="mr-1" color="#570df8" />}
          />
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
  );
}
