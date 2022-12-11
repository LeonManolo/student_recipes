import CreateIngedrient from "../../../components/CreateIngredient";

export default async function CreateRecipe() {
  return (
    <div className="grid place-content-center ...">
      <h1 className="ml-10 mt-10">
        Neues Rezept
      </h1>
      <ul>
        <li className="ml-10 mt-10">
          Name des Rezepts <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
        </li>
        <li className="ml-10 mt-10">
          Kurzbeschreibung <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
        </li>
        <li className="ml-10 mt-10">
          Preis (€) <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
        </li>
        <li className="ml-10 mt-10">
          Dauer (min) <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
        </li>
        <li className="ml-10 mt-10">
          Schwierigkeit <select className="select select-bordered w-full max-w-xs ml-10">
            <option selected>Einfach</option>
            <option>Mittel</option>
            <option>Schwer</option>
          </select>
        </li>
        <li className="ml-10 mt-10">
          Kategorie <input type="checkbox" className="checkbox ml-10" /> Kuchen <input type="checkbox" className="checkbox ml-10" /> Nudeln <input type="checkbox" className="checkbox ml-10" /> Reis <input type="checkbox" className="checkbox ml-10" /> Fleisch <input type="checkbox" className="checkbox ml-10" /> Vegetarisch
        </li>
        <li className="ml-10 mt-10">
          <div className="overflow-x-auto">
            Zutaten
            <table className="table border-solid border-2 border-black w-full max-w-xs ml-40">
              <thead>
                <tr>
                  <th>Menge</th>
                  <th>Zutat</th>
                </tr>
              </thead>
              <tbody>
                <tr className="hover">
                  <td>1</td>
                  <td>A</td>
                </tr>
                <tr className="hover">
                  <td>2</td>
                  <td>B</td>
                </tr>
              </tbody>
            </table>
          </div>
        </li>
        <li className="ml-10 mt-10">
          Bilder <input type="file" className="file-input file-input-bordered w-full max-w-xs ml-10" />
        </li>
        <li className="ml-10 mt-10">
          Zubereitung <textarea className="textarea textarea-bordered ml-10" placeholder="Hier schreiben" />
        </li>
        <li>
          <table>
            <tr><td><div contentEditable>I am editable</div></td><td><div contentEditable>I am also editable</div></td></tr>
            <tr><td>I am not editable</td></tr>
          </table>
        </li>
      </ul>
    </div>
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