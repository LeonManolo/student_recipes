"use client";
import Login from "./Login";
import Register from "./Register";
import { useEffect, useState } from "react";
import Cookies from "js-cookie";

/* Component that can renders either Login or Register component depending on user input. */
export default function Login_Register({ children, ...props }: any) {
  const [tab, setTab] = useState(0);
  //Cookies.get("token") === undefined;
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const cookie = Cookies.get("token");
    if (cookie !== undefined) {
      setIsLoggedIn(true);
    }
  }, []);

  if (isLoggedIn) {
    return <div></div>;
  }
  //{tab == 0 ? <Login onchangeRegister={() => setTab(1)} /> : <Register onchangeRegister={() => setTab(0)}

  return tab === 0 ? (
    <Login onchangeRegister={() => setTab(1)} onSuccessfulLogin={() => setIsLoggedIn(true)} />
  ) : (
    <Register onchangeRegister={() => setTab(0)} />
  );
}
