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
                                <th>Nährstoffe<IoNutritionSharp /></th>
                                <th>Menge</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th>1</th>
                                <td>Kalorien</td>
                                <td>{recipe.totalCalories}</td>
                                <td><HiFire /></td>
                            </tr>
                            <tr>
                                <th>2</th>
                                <td>Proteine</td>
                                <td>{recipe.totalProtein}</td>
                                <td><GiMuscleUp /></td>
                            </tr>
                            <tr>
                                <th>3</th>
                                <td>Kohlenhydrate</td>
                                <td>{recipe.totalCarbohydrates}</td>
                                <td><GiSlicedBread /></td>
                            </tr>
                            <tr>
                                <th>4</th>
                                <td>Fette</td>
                                <td>{recipe.totalFat}</td>
                                <td><GiDroplets /></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}