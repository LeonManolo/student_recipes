import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import { HiChevronDoubleRight, HiStar } from "react-icons/hi2";
import { HiOutlineStar } from "react-icons/hi";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import { useEffect, useState } from "react";

export default function RecipeTable({ recipe }: { recipe: RecipeResponseDto }) {
  const [isLiked, setIsLiked] = useState(false);

  useEffect(() => {
    fetchFavoriteState();
  }, []);

  const stepList = recipe.steps.map((step) => {
    return step.ingredients.map((ingredientInfo) => {
      return (
        <li key={step.stepNumber} className="mb-4">
          <div>{step.title}</div>
          <div className="flex flex-row">
            <HiChevronDoubleRight size={24} />
            <div className="mx-1">{ingredientInfo.ingredient.title}</div>
            <div className="mx-1">{ingredientInfo.amount}</div>
            <div className="mx-1">{ingredientInfo.unit}</div>
          </div>
          <div>{step.description}</div>
        </li>
      );
    });
  });

  return (
    <div className="card w-full bg-base-100 shadow-xl">
      <div className="card-body">
        <div className="flex flex-row">
          {isLiked ? (
            <HiStar
              onClick={favoriteRecipe}
              size={30}
              className="m-2 hover:scale-125 transition-all duration-500 cursor-pointer"
            />
          ) : (
            <HiOutlineStar
              onClick={favoriteRecipe}
              size={30}
              className="m-2 hover:scale-125 transition-all duration-500 cursor-pointer"
            />
          )}
          <h2 className="card-title">Step by Step Anleitung für {recipe.servings} Portionen </h2>
        </div>
        <ol className="list-decimal">{stepList}</ol>
      </div>
    </div>
  );

  async function fetchFavoriteState() {
    const client = new StudentRecipesClient();
    const recipes = await client.getFavoriteRecipes();
    const liked = recipes.find((r) => r.id == recipe.id) !== undefined;
    setIsLiked(liked);
  }

  async function favoriteRecipe() {
    const client = new StudentRecipesClient();
    if (isLiked) {
      client.unfavoriteRecipe(recipe.id);
    } else {
      await client.favoriteRecipe(recipe.id);
    }
    setIsLiked(!isLiked);
  }
}
