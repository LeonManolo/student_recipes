import CreateAvatar from '../../components/CreateAvatar';
import RecipeOverview from '../../components/RecipeOverview';
import UserInfo from '../../components/UserInfo';
export default async function Profile() {
    return (
        <div>
            <div className='flex flex-row justify-center'>
                <CreateAvatar />
            </div>
        </div>
    );
}