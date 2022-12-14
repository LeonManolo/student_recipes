"use client"
import { useState } from "react";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import RegisterRequestDto from "../utils/dto/RegisterRequestDto";

export default function Register({ children, ...props }: any) {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    return (
        <div className="card flex-shrink-0 w-full max-w-sm shadow-2xl bg-base-100">
            <div className="card-body">
                <div className="form-control">
                    <label className="label">
                        <span className="label-text">Vorname</span>
                    </label>
                    <input type="text" placeholder="Vorname" className="input input-bordered" onChange={(e) => setFirstName(e.target.value)} />
                </div>
                <div className="form-control">
                    <label className="label">
                        <span className="label-text">Nachname</span>
                    </label>
                    <input type="text" placeholder="Nachname" className="input input-bordered" onChange={(e) => setLastName(e.target.value)} />
                </div>
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
                                <a href="#" onClick={() => props.onchangeRegister()} className="label-text-alt link link-hover">schon registriert?</a>
                            </label>
                        </div>
                    </div>
                </div>
                <div className="flex form-control mt-6">
                    <button onClick={() => createAccount()} className="btn btn-primary w-1/2">Account erstellen</button>
                </div>
            </div>
        </div>
    )
    async function createAccount() {
        const recipeClient = new StudentRecipesClient();
        await recipeClient.register({
            firstName: firstName,
            lastName: lastName,
            email: email,
            password: password
        })
    }
}