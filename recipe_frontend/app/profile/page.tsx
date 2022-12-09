import CreateAvatar from '../../components/CreateAvatar';
import RecipeOverview from '../../components/RecipeOverview';
import UserInfo from '../../components/UserInfo';
export default async function Profile() {
    return (
        <div className='flex flex-row p-8 justify-center'>
            <CreateAvatar />
        </div>
    );
}
/* {
    "firstName": "hans",
    "lastName": "Mustermann",
    "email": "hans@mustermann.de",
    profile picture
} */
