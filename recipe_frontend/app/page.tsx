"use client";
import Cookies from "js-cookie";
import Image from "next/image";
import { useRouter } from "next/navigation";
import AddIngredientModal from "../components/AddIngredientModal";
import Login from "../components/Login";
import Login_Register from "../components/Login_Register";
import Register from "../components/Register";

export default function HomePage() {
  const router = useRouter()
  return (
    <div className="hero min-h-screen bg-base-200" style={{ backgroundImage: `url("/cooking.jpg")` }}>
      <div className="hero-overlay bg-opacity-60"></div>
      <div className="hero-content flex flex-row gap-x-32 lg:flex-row-reverse ">
        <Login_Register />
        <button
          className="btn"
          onClick={() => {
            Cookies.remove("token");
            router.refresh();
          }}
        >
          DELETE COOKIES
        </button>
        <div className="text-center text-white">
          <h1 className="text-5xl font-bold ">Studentenkueche</h1>
          <p className="py-6">Leckere Rezepte für leckere Studenten</p>
        </div>
      </div>
    </div>
  );
}
