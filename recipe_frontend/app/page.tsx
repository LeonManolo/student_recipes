import Image from "next/image";
import AddIngredientModal from "../components/AddIngredientModal";
import Login from "../components/Login";
import Login_Register from "../components/Login_Register";
import Register from "../components/Register";

export default function HomePage() {
  return (
    <div className="hero min-h-screen bg-base-200" style={{ backgroundImage: `url("/cooking.jpg")` }}>
      <div className="hero-overlay bg-opacity-60"></div>
      <div className="hero-content flex-col lg:flex-row-reverse ">
        <AddIngredientModal />
        <Login_Register />
        <div className="text-center text-white">
          <h1 className="text-5xl font-bold ">Studentenkueche</h1>
          <p className="py-6">Leckere Rezepte für leckere Studenten</p>
        </div>
      </div>
    </div>
  );
}

