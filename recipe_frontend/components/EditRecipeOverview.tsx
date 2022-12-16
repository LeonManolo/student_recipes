import img from "../public/food/pizza3.png";
import Image from "next/image";
import { FiClock } from "react-icons/fi";
import { HiOutlineChartBar } from "react-icons/hi2";
import { HiFire } from "react-icons/hi";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import React from "react";
import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import { HiOutlineCurrencyEuro } from "react-icons/hi2";
import Link from "next/link";
import { Rating } from "@mui/material";
import { BsHeartFill, BsHeart } from "react-icons/bs";

export default function EditRecipeOverview({ recipe }: { recipe: RecipeResponseDto }) {
  const recipeClient = new StudentRecipesClient();
  return (
    <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
      <Link href={`recipes/${recipe.id}`}>
        {recipe && <Image className="rounded-t-xl " src={recipe.imageUrl!!} alt="recipe" width={500} height={500} />}
      </Link>
      <div className="card-body">
        <div className="flex flex-col">
          <h1 className="card-title text-3xl">{recipe.title}</h1>
          <Rating
            readOnly={true}
            name="size-medium"
            defaultValue={recipe.averageRating}
            value={recipe.averageRating}
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

        <div className="flex flex-row">
          <button
            onClick={() => {
              const result = window.confirm("Bist du sicher, dass du das Rezept für immer löschen möchtest?");
              if (result) {
                recipeClient.deleteRecipe(recipe.id);
              }
            }}
            className="btn bg-custom-first hover:bg-custom-second"
          >
            Löschen
          </button>
        </div>
        <Link href={`/my-recipes/edit-recipe/${recipe.id}`}>
          <button className="btn bg-custom-first hover:bg-custom-second">Bearbeiten</button>
        </Link>
      </div>
    </div>
  );
}
