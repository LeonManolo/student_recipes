import RecipeOverview from '../../../components/RecipeOverview';
import RecipeTable from '../../../components/RecipeTable';
import Image from 'next/image';
import img from '../../../public/food/pizza3.png'
import { FiClock } from "react-icons/fi";
import { FiThumbsUp } from "react-icons/fi";
import { HiFire } from "react-icons/hi";
export default async function RecipeDetail() {
  return (
    <div className='flex justify-center mt-4'>
      <div className='card shadow-xl'>
        <div className='card-body flex-row justify-center'>
          <RecipeOverview></RecipeOverview>
          <div className='w-1/2'>
            <RecipeTable></RecipeTable>
          </div>
        </div>
      </div>
    </div>
  );
}