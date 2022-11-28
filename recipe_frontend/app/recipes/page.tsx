// Mapped zu der Route "/recipes" ohne eine id also nicht "recipes/123"
export default async function Recipes() {
  const recipes = await fetchRecipes();

  return (
    <div>
      {/* In gescheiften Klammern kann Javascript/Typescript Code geschrieben werden */}
      <h1>Hier sollte eine liste von rezepten angezeigt werden</h1>
      {recipes.map((element) => (
        <div id={element.id}>Das ergebnis {element.title}</div>
      ))}
    </div>
  );
}

async function fetchRecipes(): Promise<any[]> {
  const result = await fetch("https://dummyjson.com/products", { cache: "no-store" }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
  const recipes = await result.json();
  return recipes.products; // für die dummmy api musste ich products aufrufen
}
