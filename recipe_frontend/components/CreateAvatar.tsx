import Image from 'next/image';
import img from '../public/profile/profile-picture.png';
export default function CreateAvatar({ children, ...props }: any) {
    return (
        <div className="card card-side bg-base-100 shadow-xl p-10">
            <figure><div className="avatar">
                <div className="p-4">
                    <div className="w-1/2 rounded">
                        <Image className="rounded border-double border-8 border-violet-900" src={img} alt='profile-picture' width={300} height={300} />
                    </div>
                    <div className="p-2">
                        <input type="file" className="file-input file-input-bordered file-input-primary w-full max-w-xs" />
                    </div>
                </div>
            </div></figure>
            <div className="card-body">
                <div className="stats stats-vertical shadow">
                    <div className="stat">
                        <div className="stat-title">Vorname: </div>
                        <div className="stat-value">firstName</div>
                        {/*                 <div className="stat-desc">Jan 1st - Feb 1st</div> */}
                    </div>

                    <div className="stat">
                        <div className="stat-title">Nachname: </div>
                        <div className="stat-value">lastName</div>
                    </div>

                    <div className="stat">
                        <div className="stat-title">E-Mail: </div>
                        <div className="stat-value">hans@mustermann.de</div>
                    </div>
                </div>
            </div>
        </div>
    )
}