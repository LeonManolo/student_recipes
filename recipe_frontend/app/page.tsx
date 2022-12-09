import Image from "next/image";
import Login from "../components/Login";
import Register from "../components/Register";

export default function HomePage() {
  return (
    <div className="hero min-h-screen bg-red-500" style={{ backgroundImage: `url("/cooking.jpg")` }}>
      <div className="hero-overlay bg-opacity-60 justify-center">
        <div className="hero-content text-center text-neutral-content">
          <div className="max-w-md">
            <h1 className="mb-5 text-5xl font-bold">Studentenkueche</h1>
            <p className="mb-5">
              Leckere Rezepte für leckere Studenten
            </p>
          </div>
          <div className="flex justify-end m-16 align-middle w-96">
            <Login></Login>
          </div>
          {/*           <Register></Register> */}
        </div>
      </div>
    </div>
  );
}

