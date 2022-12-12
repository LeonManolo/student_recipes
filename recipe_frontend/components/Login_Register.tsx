
"use client";
import Login from "./Login";
import Register from "./Register";
import { useState } from "react";

export default function Login_Register({ children, ...props }: any) {
    const [tab, setTab] = useState(0);
    return (
        <div>
            {tab == 0 ? <Login onchangeRegister={() => setTab(1)} /> : <Register onchangeRegister={() => setTab(0)} />}
        </div>
    )
}