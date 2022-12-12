"use client";
import React from "react";
import StudentRecipesClient from "../../../utils/StudentRecipesClient";

export default function CreateRecipePage() {
  return (
    <form onSubmit={handleSubmit}>
      <div className="grid place-content-center ...">
        <h1 className="ml-10 mt-10">Neues Rezept</h1>
        <div>
          <ul>
            <li className="ml-10 mt-10">
              Name des Rezepts{" "}
              <input
                name="title"
                type="text"
                placeholder="Hier schreiben"
                className="input input-bordered w-full max-w-xs ml-10"
              />
            </li>
            <li className="ml-10 mt-10">
              Kurzbeschreibung{" "}
              <input
                name="title"
                type="text"
                placeholder="Hier schreiben"
                className="input input-bordered w-full max-w-xs ml-10"
              />
            </li>
            <li className="ml-10 mt-10">
              Preis (€){" "}
              <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
            </li>
            <li className="ml-10 mt-10">
              Dauer (min){" "}
              <input type="text" placeholder="Hier schreiben" className="input input-bordered w-full max-w-xs ml-10" />
            </li>
            <li className="ml-10 mt-10">
              Schwierigkeit{" "}
              <select className="select select-bordered w-full max-w-xs ml-10">
                <option>Einfach</option>
                <option>Mittel</option>
                <option>Schwer</option>
              </select>
            </li>
            <li className="ml-10 mt-10">
              Kategorie <input type="checkbox" className="checkbox ml-10" /> Kuchen{" "}
              <input type="checkbox" className="checkbox ml-10" /> Nudeln{" "}
              <input type="checkbox" className="checkbox ml-10" /> Reis{" "}
              <input type="checkbox" className="checkbox ml-10" /> Fleisch{" "}
              <input type="checkbox" className="checkbox ml-10" /> Vegetarisch
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
                      <td className="p-2" contentEditable="true"></td>
                      <td className="p-2" contentEditable="true"></td>
                    </tr>
                    <tr className="hover">
                      <td className="p-2" contentEditable="true"></td>
                      <td className="p-2" contentEditable="true"></td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </li>
            <li className="ml-10 mt-10">
              Bilder <input type="file" className="file-input file-input-bordered w-full max-w-xs ml-10" />
            </li>
            <li className="ml-10 mt-10">
              Zubereitung{" "}
              <textarea className="textarea textarea-bordered w-full max-w-xs ml-10" placeholder="Hier schreiben" />
            </li>
            <li className="ml-10 mt-10 mb-10">
              <input type="submit" value="Speichern" className="btn" />{" "}
              <button className="btn float-right mr-auto">Abbrechen</button>
            </li>
          </ul>
        </div>
      </div>
    </form>
  );

  async function handleSubmit(event: any) {
    event.preventDefault();
    const target = event.currentTarget;
    //for simple html input values
    //const formInput = (e.target as HTMLFormElement).files[0];

    console.log(target.title.value);

    const recipeClient = new StudentRecipesClient();
      const result = await recipeClient
      .createRecipe(
        {
          title: "Burger",
          description: "Tolles Burger rezept für burgerliebhaber",
          servings: 2,
          authorId: 1,
          steps: []
        }
      ).catch(e => {
        console.log("DER ERROR: " + e)
      })
    }

}
