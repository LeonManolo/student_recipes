"use client";

import { useState } from "react";
import CreateIngredientRequestDto from "../utils/dto/CreateIngredientRequestDto";
import IngredientDto from "../utils/dto/IngredientDto";
import UserDto from "../utils/dto/UserDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";

export default function AddIngredientModal({
  onAddIngredientClick,
  modalId,
}: {
  onAddIngredientClick: (ingredient: IngredientDto) => void,
  modalId: string,
}) {
  const [tab, setTab] = useState(0);
  const [user, setUser] = useState<UserDto>();

  return (
    <div className="justify-center">
      <a href={`#${modalId}`} className="btn">
        Zutat hinzufügen +
      </a>
      <div className="modal" id={modalId}>
        <div className="modal-box">
          <div className="tabs w-full">
            <div className="w-full"></div>
            <a className={`tab tab-lg tab-lifted ${tab == 0 ? "tab-active" : ""}`} onClick={() => setTab(0)}>
              Zutat suchen
            </a>
            <a className={`tab tab-lg tab-lifted ${tab == 1 ? "tab-active" : ""}`} onClick={() => setTab(1)}>
              Zutat erstellen
            </a>
          </div>

          {tab == 0 ? <SearchIngredient onAddIngredientClick={onAddIngredientClick}/> : <CreateIngredient />}
          <div className="modal-action">
            <a href="#" className="btn">
              Abbrechen
            </a>
          </div>
        </div>
      </div>
    </div>
  );

  async function createIngredient(): Promise<void> {
    console.log("pressed createIngredient");
    const ingredient: CreateIngredientRequestDto = {
      title: "Kartoffel",
      calories: 12,
      protein: 22,
      carbohydrates: 12,
      fat: 33,
    };
    try {
      const recipeClient = new StudentRecipesClient();
      const success = await recipeClient.createIngredient(ingredient);
      console.log(success);
    } catch (e: any) {
      console.log(e?.message);
    }
  }
}

function SearchIngredient({onAddIngredientClick}: {onAddIngredientClick: (x: IngredientDto) => void}) {
  const [ingredients, setIngredients] = useState<IngredientDto[]>([]);
  const [isLoading, setLoading] = useState(false);
  let searchText = "";

  return (
    <div className="form-control w-full max-w-xs pt-6">
      <input
        type="text"
        placeholder="Tomaten..."
        onChange={(event) => {
          searchText = event?.target.value;
          fetchIngredients();
        }}
        className="input input-bordered w-full max-w-xs"
      />
      {isLoading ? "Loading..." : ""}
      {ingredients.map((e) => (
        <a href="#" onClick={() => onAddIngredientClick(e)} key={e.id}>{e.title}</a>
      ))}
    </div>
  );

  async function fetchIngredients(): Promise<void> {
    setLoading(true);
    const recipeClient = new StudentRecipesClient();
    //TODO hier searchText
    console.log(searchText);
    const fetchedIngredients = await recipeClient.getIngredients(); //TODO: filter hinzufügen
    setLoading(false);
    setIngredients(fetchedIngredients);
  }
}

function CreateIngredient() {
  return <div>NAda</div>;
}
