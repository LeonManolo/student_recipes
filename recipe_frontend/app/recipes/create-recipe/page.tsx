import CreateIngedrient from "../../../components/CreateIngredient";

export default async function CreateRecipe() {
  return (
    <div>
      <CreateIngedrient></CreateIngedrient>
    </div >
  );

  async function createIngedrients(): Promise<void> {
    const json = JSON.stringify({
      "locale": "de",
      "title": "Gurke",
      "calories": 135,
      "protein": 22,
      "carbohydrates": 12,
      "fat": 33
    });
    const result = await fetch("https://sea-turtle-app-hqisk.ondigitalocean.app/api/ingredients", { method: "POST", body: json }); // cache no store um bei jedem Seiten aufruf die Rezepte zu fetchen
    console.log(result.status)
  }
}