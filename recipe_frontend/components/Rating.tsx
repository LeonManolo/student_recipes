import img from '../public/food/pizza3.png'
import Image from 'next/image';
import { FiClock } from "react-icons/fi";
import { HiOutlineChartBar } from "react-icons/hi2";
import { HiFire } from "react-icons/hi";
import { HiOutlineStar } from "react-icons/hi";
import { HiOutlineFaceSmile } from "react-icons/hi2";
import React, { useState } from 'react';
import RecipeResponseDto from '../utils/dto/RecipeResponseDto';
import StudentRecipesClient from '../utils/StudentRecipesClient';
import { HiOutlineCurrencyEuro } from "react-icons/hi2";

export default function Rating({ rating }: { rating: number }) {
    return (
        <div className='flex flex-row'>
            <div className="rating gap-1 mt-4">
                <input type="radio" name="rating-1" className="mask mask-heart bg-custom-first" checked={rating === 1} />
                <input type="radio" name="rating-2" className="mask mask-heart bg-custom-first" checked={rating === 2} />
                <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" checked={rating === 3} />
                <input type="radio" name="rating-4" className="mask mask-heart bg-custom-first" checked={rating === 4} />
                <input type="radio" name="rating-5" className="mask mask-heart bg-custom-first" checked={rating === 5} />
            </div>
        </div>
    )
}