"use client"
import { useState } from "react";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import LoginRequestDto from "../utils/dto/LoginRequestDto";

export default function Login({ children, ...props }: any) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    return (
        <div className="card flex-shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
            <div className="card-body">
                <div className="form-control">
                    <label className="label">
                        <span className="label-text">E-Mail</span>
                    </label>
                    <input type="text" placeholder="E-Mail" className="input input-bordered" onChange={(e) => setEmail(e.target.value)} />
                </div>
                <div className="form-control">
                    <label className="label">
                        <span className="label-text">Passwort</span>
                    </label>
                    <input type="text" placeholder="Passwort" className="input input-bordered" onChange={(e) => setPassword(e.target.value)} />
                    <div className="flex w-full">
                        <div className="w-full">
                            <label className="label">
                                <a href="#" className="label-text-alt link link-hover">Passwort vergessen?</a>
                            </label>
                        </div>
                        <div className="w-full">
                            <label className="label">
                                <a href="#" onClick={() => props.onchangeRegister()} className="label-text-alt link link-hover">Account erstellen</a>
                            </label>
                        </div>
                    </div>
                </div>
                <div className="flex form-control mt-6">
                    <button onClick={() => logintoAccount()} className="btn btn-primary w-1/2">Login</button>
                </div>
            </div>
        </div>
    )
    function logintoAccount() {
        const recipeClient = new StudentRecipesClient();
        recipeClient.login({
            email: email,
            password: password
        })
    }
}