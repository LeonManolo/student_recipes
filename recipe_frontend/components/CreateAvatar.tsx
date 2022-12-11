import Image from 'next/image';
import img from '../public/profile/profile-picture.png';
import { HiStar } from "react-icons/hi";
import Link from 'next/link';
export default function CreateAvatar({ children, ...props }: any) {
    return (
        <div className="card card-side shadow-xl p-10">
            <figure><div className="avatar">
                <div className="p-4">
                    <div className="w-1/2 rounded">
                        <Image className="rounded border-double border-8" src={img} alt='profile-picture' width={300} height={300} />
                    </div>
                    <div className="form-control max-w-xs w-3/4 pt-2">
                        <input type="file" className="file-input file-input-bordered max-w-xs " />
                        <label className="label">
                            <span className="label-text-alt">Neues Bild hochladen</span>
                        </label>
                    </div>
                    <Link href="/favorites">
                        <button className="btn gap-2 bg-custom-first mt-8 hover:bg-custom-second">
                            Favoriten
                            <HiStar size={24}></HiStar>
                        </button>
                    </Link>
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