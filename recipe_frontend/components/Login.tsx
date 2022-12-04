export default function Login({ children, ...props }: any) {
    return (
        <div className="card bg-base-100 shadow-xl">
            <div className="card-body ">
                <h2 className="card-title">Login</h2>
                <div>
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text">E-Mail</span>
                        </label>
                        <input type="text" placeholder="" className="input input-bordered w-full max-w-xs" />
                    </div>
                    <div className="form-control w-full max-w-xs">
                        <label className="label">
                            <span className="label-text">Passwort</span>
                        </label>
                        <input type="text" placeholder="" className="input input-bordered w-full max-w-xs" />
                    </div>
                </div>
            </div>
        </div>
    )
}