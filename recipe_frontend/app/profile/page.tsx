import CreateAvatar from '../../components/CreateAvatar';
import UserInfo from '../../components/UserInfo';
export default async function Profile() {
    return (
        <div className='flex flex-row p-8'>
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
