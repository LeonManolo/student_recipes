import RecipeOverview from '../../../components/RecipeOverview';
import RecipeTable from '../../../components/RecipeTable';
export default async function RecipeDetail() {
    return (
        <div className='flex flex-row p-8'>
            <div> <RecipeOverview></RecipeOverview></div>
            <div className='p-8 w-1/3 h-1/3'>
                <div className="card bg-base-50 shadow-xl">
                    <div className="card-body">
                        <h2 className="card-title">Zubereitung</h2>
                        <p>
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                        </p>
                    </div>
                </div>
            </div>
            <div><RecipeTable></RecipeTable></div>
        </div>
    );
}