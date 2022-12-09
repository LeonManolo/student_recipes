import RecipeOverview from '../../../components/RecipeOverview';
import RecipeTable from '../../../components/RecipeTable';
import Image from 'next/image';
import img from '../../../public/food/pizza3.png'
import { FiClock } from "react-icons/fi";
import { FiThumbsUp } from "react-icons/fi";
import { HiFire } from "react-icons/hi";
export default async function RecipeDetail() {
  return (
    <div className='card shadow-xl h-full w-full'>
      <div className='card-body'>
        <div className='flex flex-col ml-8'>
          <div className='flex mt-8 my-2'>
            <h1 className='text-3xl font-bold'>Pizza mit Gemüse</h1>
          </div>
          <div className='flex '>
            <div className='flex flex-col'>
              <Image className='rounded-xl' src={img} alt="recipe" width={500} height={500} />
              <div className='flex justify-center'>
                <div className='flex flex-row mt-4'>
                  <HiFire size="20" className='mr-1'></HiFire>
                  <p>Kalorien</p>
                  <FiClock size="20" className='ml-4 mr-1'></FiClock>
                  <p>Zubereitungsdauer</p>
                  <FiThumbsUp size="20" className='ml-4 mr-1'></FiThumbsUp>
                  <p>Gefällt mir</p>
                </div>
              </div>
            </div>
            <div>
              <div className='flex flex-row'>
                <div className="rating rating-lg m-2">
                  <input type="radio" name="rating-1" className="mask mask-star bg-yellow-400" />
                </div>
{/*                 <h2 className='flex my-2'>Favorit</h2> */}
              </div>
              <div className='flex flex-col'>
                <div className="rating gap-1 m-4">
                  <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                  <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                  <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                  <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                  <input type="radio" name="rating-3" className="mask mask-heart bg-custom-first" />
                </div>
{/*                 <h2 className='flex my-2'>Bewerten</h2> */}
              </div>
            </div>
            <div className='flex justify-center'>
              <div className='w-full h-1/3 mx-32'>
                <div className="card shadow-xl">
                  <div className="card-body rounded-2xl">
                    <h2 className="card-title">Zubereitung</h2>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi aliquet eleifend massa pellentesque vehicula. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Etiam lectus lectus, auctor at facilisis ut, porttitor et felis. Curabitur elementum dictum sapien vel iaculis. Nullam a metus rhoncus augue commodo fermentum sit amet sit amet lorem. Donec semper neque vel ultrices placerat. Quisque at massa ac libero efficitur euismod suscipit quis felis. Curabitur pharetra eros a nisi finibus rhoncus. Maecenas augue odio, scelerisque sit amet eleifend at, malesuada non tellus. Praesent id est at justo sagittis ornare. Ut elementum posuere felis, vel finibus arcu dignissim sed. Pellentesque pulvinar sed ligula sit amet rhoncus. Suspendisse vitae lacus porttitor, finibus neque ac, eleifend sapien. Donec fringilla auctor sapien in congue.

                      Nullam gravida turpis egestas sollicitudin commodo. Praesent et purus diam. Integer eget viverra augue. Aliquam erat volutpat. Vivamus ut ultrices ipsum, in iaculis nibh. Vivamus mattis porttitor magna, non porta velit semper bibendum. Proin eget arcu quis nisl luctus venenatis eget ut leo.

                      Vivamus tempor maximus efficitur. Mauris volutpat volutpat mauris, et tristique nunc volutpat quis. Praesent nunc nulla, dignissim ut accumsan et, dictum nec ipsum. Sed gravida in urna id laoreet. Vivamus quis enim ut nunc condimentum bibendum. Praesent magna sapien, blandit nec vulputate a, dignissim vitae dolor. Sed vehicula sem at lacus vestibulum, ut convallis erat luctus. Mauris nec leo libero.</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}