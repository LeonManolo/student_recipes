import Image from "next/image";
import Login from "../components/Login";

export default function HomePage() {
  return (
    <div className="hero min-h-screen bg-red-500" style={{ backgroundImage: `url("/cooking.jpg")` }}>
      <div className="hero-overlay bg-opacity-60"></div>
      <div className="hero-content text-center text-neutral-content">
        <div className="max-w-md">
          <h1 className="mb-5 text-5xl font-bold">Leckere Rezepte für leckere Studenten</h1>
          <p className="mb-5">
            Provident cupiditate voluptatem et in. Quaerat fugiat ut assumenda excepturi exercitationem quasi. In
            deleniti eaque aut repudiandae et a id nisi.
          </p>
          <button className="btn btn-primary">Get Started</button>
        </div>
      </div>
    </div>
  );
}

