import img from '../public/food/pizza3.png'
import Image from 'next/image';
import { FiClock } from "react-icons/fi";
import { HiOutlineChartBar } from "react-icons/hi2";
import { HiFire } from "react-icons/hi";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import React from 'react';
import RecipeResponseDto from '../utils/dto/RecipeResponseDto';
import StudentRecipesClient from '../utils/StudentRecipesClient';
import { HiOutlineCurrencyEuro } from "react-icons/hi2";
import Link from 'next/link';

export default function EditRecipeOverview({ recipe }: { recipe: RecipeResponseDto }) {
    const recipeClient = new StudentRecipesClient()
    return (
        <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
            <Image className='rounded-t-xl ' src={img} alt="recipe" width={500} height={500} />
            <div className="card-body">
                <div className='flex flex-col'>
                    <h1 className="card-title text-3xl">{recipe.title}</h1>
                    <div className="rating gap-1 my-4">
                        <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                    </div>
                </div>
                <h3 className='text-slate-500'>{recipe.description}</h3>
                <div className='flex flex-row'>
                    <HiFire size="20" className='mr-1'></HiFire>
                    <p>{recipe.totalCalories}</p>
                    <FiClock size="20" className='mr-1'></FiClock>
                    <p>{recipe.cookTime}</p>
                    <HiOutlineCurrencyEuro size="20" className='mr-1'></HiOutlineCurrencyEuro>
                    <p>{recipe.price}</p>
                    <HiOutlineChartBar size="20" className='mr-1'></HiOutlineChartBar>
                    <p>{recipe.views}</p>
                    <HiOutlineFaceSmile size="20" className='mr-1'></HiOutlineFaceSmile>
                    <p>{recipe.author.firstName}</p>
                </div>

                <div className='flex flex-row'>
                    <button onClick={() => {
                        const result = window.confirm("Bist du sicher, dass du das Rezept für immer löschen möchtest?");
                        if (result) {
                            recipeClient.deleteRecipe(recipe.id)
                        }
                    }} className="btn bg-custom-first hover:bg-custom-second">Löschen</button>
                </div>
                <Link href="/edit-recipe">
                    <button className="btn bg-custom-first hover:bg-custom-second">
                        Bearbeiten
                    </button>
                </Link>
            </div>
        </div>
    )

}