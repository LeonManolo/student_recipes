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
          <div className="alert alert-error shadow-lg">
            <div>
              <svg xmlns="http://www.w3.org/2000/svg" className="stroke-current flex-shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
              <span>Error! Task failed successfully.</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
