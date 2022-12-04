import RecipeOverview from '../../../components/RecipeOverview';
import RecipeTable from '../../../components/RecipeTable';
export default async function RecipeDetail() {
  return (
    <div className='flex justify-center mt-16'>
      <div className='card shadow-xl w-3/4'>
        <div className='card-body flex-row justify-center'>
          <div> <RecipeOverview></RecipeOverview></div>
          <div className="rating rating-lg">
            <input type="radio" name="rating-1" className="mask mask-star bg-yellow-400" />
          </div>
          <div className='px-24 py-2'><RecipeTable></RecipeTable></div>
          <div className='w-1/3 h-1/3'>
            <div className="card shadow-xl">
              <div className="card-body bg-custom-third rounded-2xl">
                <h2 className="card-title">Zubereitung</h2>
                <p>
                  Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}