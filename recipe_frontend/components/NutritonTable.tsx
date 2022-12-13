import img from '../public/food/pizza3.png'
import Image from 'next/image';
import { FiClock } from "react-icons/fi";
import { HiOutlineChartBar } from "react-icons/hi2";
import { HiFire } from "react-icons/hi";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import { GiMuscleUp } from "react-icons/gi";
import { GiSlicedBread } from "react-icons/gi";
import { GiDroplets } from "react-icons/gi";
import { IoNutritionSharp } from "react-icons/io5";

import React from 'react';
import RecipeResponseDto from '../utils/dto/RecipeResponseDto';
import StudentRecipesClient from '../utils/StudentRecipesClient';

export default function NutritionTable({ recipe }: { recipe: RecipeResponseDto }) {
    return (
        <div className="card card-compact w-96 h-max shadow-xl bg-base-100 ">
            <div className="card-body">
                <div className="overflow-x-auto">
                    <table className="table table-zebra w-full">
                        <thead>
                            <tr>
                                <th></th>
                                <th className='flex flex-row'> Nährstoffe</th>
                                <th>Menge</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><HiFire size={20} /></td>
                                <td>Kalorien</td>
                                <td>{recipe.totalCalories}</td>
                            </tr>
                            <tr>
                                <td><GiMuscleUp size={20} /></td>
                                <td>Proteine</td>
                                <td>{recipe.totalProtein}</td>
                            </tr>
                            <tr>
                                <td><GiSlicedBread size={20} /></td>
                                <td>Kohlenhydrate</td>
                                <td>{recipe.totalCarbohydrates}</td>
                            </tr>
                            <tr>
                                <td><GiDroplets size={20} /></td>
                                <td>Fette</td>
                                <td>{recipe.totalFat}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}