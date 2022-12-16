"use client";

import { useState } from "react";
import CreateIngredientRequestDto from "../utils/dto/CreateIngredientRequestDto";
import IngredientDto from "../utils/dto/IngredientDto";
import UserDto from "../utils/dto/UserDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";
import LoadingDots from "./LoadingDots";

export default function AddIngredientModal({
  onAddIngredientClick,
  modalId,
}: {
  onAddIngredientClick: (ingredient: IngredientDto) => void;
  modalId: string;
}) {
  const [tab, setTab] = useState(0);

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
          {tab == 0 ? (
            <SearchIngredient onAddIngredientClick={onAddIngredientClick} />
          ) : (
            <CreateIngredient onIngredientClick={onAddIngredientClick} />
          )}
          <div className="modal-action">
            <a href="#" className="btn">
              Abbrechen
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}

function SearchIngredient({ onAddIngredientClick }: { onAddIngredientClick: (x: IngredientDto) => void }) {
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
      {isLoading ? (
        <LoadingDots />
      ) : (
        ingredients.map((e) => (
          <a href="#" className="pt-6" onClick={() => onAddIngredientClick(e)} key={e.id}>
            {e.title}
          </a>
        ))
      )}
    </div>
  );

  async function fetchIngredients(): Promise<void> {
    setLoading(true);
    const recipeClient = new StudentRecipesClient();
    const fetchedIngredients = await recipeClient.getIngredients(searchText); //TODO: filter hinzufügen
    setLoading(false);
    setIngredients(fetchedIngredients);
  }
}

function CreateIngredient({ onIngredientClick }: { onIngredientClick: (v: IngredientDto) => void }) {
  const [ingredient, setIngredient] = useState<CreateIngredientRequestDto>({
    title: "",
    calories: 0,
    protein: 0,
    carbohydrates: 0,
    fat: 0,
  });

  return (
    <div>
      <InputField
        title={"Title"}
        type={"text"}
        value={ingredient.title}
        maxlength={255}
        onChange={(v) => {
          ingredient.title = v;
          setIngredient({ ...ingredient });
        }}
      />
      <InputField
        title={"Protein"}
        type={"number"}
        min={0}
        max={100}
        value={ingredient.protein}
        onChange={(v) => {
          ingredient.protein = parseInt(v);
          setIngredient({ ...ingredient });
        }}
      />
      <InputField
        title={"Fett"}
        type={"number"}
        min={0}
        max={100}
        value={ingredient.fat}
        onChange={(v) => {
          ingredient.fat = parseInt(v);
          setIngredient({ ...ingredient });
        }}
      />
      <InputField
        title={"Kohlenhydrate"}
        type={"number"}
        min={0}
        max={100}
        value={ingredient.carbohydrates}
        onChange={(v) => {
          ingredient.carbohydrates = parseInt(v);
          setIngredient({ ...ingredient });
        }}
      />
      <InputField
        title={"Kalorien"}
        type={"number"}
        min={0}
        max={5000}
        value={ingredient.calories}
        onChange={(v) => {
          ingredient.calories = parseInt(v);
          setIngredient({ ...ingredient });
        }}
      />
      <a
        href="#"
        className={`btn ${ingredient.title.length > 0 ? "" : "btn-disabled"} mt-4`}
        onClick={handleCreateIngredientSubmit}
      >
        Erstellen und Hinzufügen
      </a>
    </div>
  );

  async function handleCreateIngredientSubmit() {
    event?.preventDefault();
    try {
      const recipeClient = new StudentRecipesClient();
      //TODO: hier ingredient mit id holen
      const ingredientFetched = await recipeClient.createIngredient(ingredient);
      onIngredientClick(ingredientFetched);
    } catch (e: any) {
      console.log(e?.message);
      alert(e?.message);
    }
  }
}

export function InputField({
  title,
  type,
  min,
  max,
  value,
  placeholder,
  onChange,
}: {
  title: string;
  type: string;
  min?: number;
  max?: number;
  placeholder?: string;
  value: string | number;
  onChange: (value: string) => void;
}) {
  return (
    <div className="form-control w-full">
      <label className="label">
        <span className="label-text">{title}</span>
      </label>
      <input
        value={value}
        min={min}
        max={max}
        onChange={(v) => onChange(v.target.value)}
        type={type}
        placeholder={placeholder ?? ""}
        className="input input-bordered w-full"
        required={true}
      />
    </div>
  );
}
