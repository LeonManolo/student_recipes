import { useRouter } from "next/router"; // um zum beispiel die query parameter zu erhalten
import Link from "next/link"; // um zu navigieren

// Hier sollten alle Rezepte angezeigt werden
// index wiederspiegelt die Route ohne paramter also nur /recipe anstatt /recipe/123
// mit {data} kriegen wir das data object aus getServerSideProps
export default function Recipe({ data }) {
  const router = useRouter();
  const recipeId = router.query.id;
  // Render data...
  return (
    <div className="hero min-h-screen bg-base-200">
      <div className="hero-content text-center">
        <div className="max-w-md">
          <h1 className="text-5xl font-bold">
            Hier sollten alle Rezept angezeigt werden
          </h1>
          <p className="py-6">
            Provident cupiditate voluptatem et in. Quaerat fugiat ut assumenda
            excepturi exercitationem quasi. In deleniti eaque aut repudiandae et
            a id nisi.
          </p>
          {/* Generell kann man in geschweiften Klammern javascript code schreiben.*/}
          {/* <Link> wird benutzt um auf eine Route zu navigieren*/}
          <Link href="/">
            <button className="btn btn-primary">Zum Home Screen</button>
          </Link>
        </div>
      </div>
    </div>
  );
}

// Hier werden die Rezepte von der Spring boot Api gefetched
// This gets called on every request
export async function getServerSideProps({ id }) {
  // Fetch data from external API
  // hier der api call zur unserer Spring Boot Api
  /*
    const res = await fetch(`https://.../data`)
    const data = await res.json()
    */
  const data = { data: {} }; // danach löschen nur zum testen kurz
  console.log(id);

  // Pass data to the page via props
  return { props: { data } };
}
