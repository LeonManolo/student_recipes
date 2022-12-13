"use client";
import React, { useState } from "react";
import AddIngredientModal from "../../../components/AddIngredientModal";
import { IngredientInfoDto, RecipeStepDto } from "../../../utils/dto/CreateRecipeRequestDto";
import IngredientDto from "../../../utils/dto/IngredientDto";
import StudentRecipesClient from "../../../utils/StudentRecipesClient";

export default function CreateRecipePage() {
  let recipeSteps: RecipeStepDto[] = [];

  return (
    <div className="flex flex-row justify-center w-full p-4">
      <form onSubmit={handleSubmit} className="w-2/3 p-4 border border-base-content rounded-lg">
        <h1 className="mb-8">Rezept erstellen</h1>

        <div className="flex flex-row w-full space-x-4">
          <div className="flex items-center w-full aspect-square rounded-lg text-center justify-center bg-base-200">
            Kein Bild ausgewählt
          </div>
          <div className="flex justify-center form-control w-full">
            <label className="label">
              <span className="label-text">Bild auswählen</span>
            </label>
            <input type="file" className="file-input file-input-bordered w-full" />
          </div>
        </div>

        <div className="flex flex-row space-x-4">
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Titel des Rezeptes</span>
            </label>
            <input type="text" placeholder="Nudeln mit Pesto..." className="input input-bordered w-full" />
          </div>
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Anzahl an Portionen</span>
            </label>
            <input type="text" placeholder="3..." className="input input-bordered w-full" />
          </div>
        </div>

        <div className="form-control w-full">
          <label className="label">
            <span className="label-text">Beschreibung</span>
          </label>
          <input type="text" placeholder="Beschreibung..." className="input input-bordered w-full" />
        </div>

        <div className="flex flex-row space-x-4">
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Kochzeit in Minuten</span>
            </label>
            <input type="text" placeholder="5..." className="input input-bordered w-full" />
          </div>
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Preis in Euro</span>
            </label>
            <input type="text" placeholder="2..." className="input input-bordered w-full" />
          </div>
        </div>

        <div className="divider" />
        <RecipeStepsComponent
          onRecipeStepChange={(steps) => {
            recipeSteps = { ...steps };
            console.log("STEP ADDED!");
          }}
        />
      </form>
    </div>
  );

  function handleSubmit() {}
}

function RecipeStepsComponent({ onRecipeStepChange }: { onRecipeStepChange: (steps: RecipeStepDto[]) => void }) {
  const [steps, setSteps] = useState<RecipeStepDto[]>([]);

  return (
    <div>
      <h2 className="mb-4">Schritte</h2>
      {steps.map((recipeStep, index) => (
        <div key={`recipe_step_${index}`} className="p-4 mb-4 border rounded-lg">
          <h3>Schritt {index + 1}</h3>
          <div className="flex flex-row space-x-4">
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text">Titel des Schrittes</span>
              </label>
              <input type="text" placeholder="Reis waschen..." className="input input-bordered w-full" />
            </div>
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text">Beschreibung</span>
              </label>
              <input
                type="text"
                placeholder="Reis sorgfältig für etwa 2 Minuten waschen..."
                className="input input-bordered w-full"
              />
            </div>
          </div>

          <div>
            <div className="flex flex-col w-full border-opacity-50">
              <div className="divider">Zutaten</div>
            </div>
            {recipeStep.ingredients.map((ingredient, i) => (
              <IngredientInfoComponent key={`step_${index}_ingr_${i}`} ingredient={ingredient} />
            ))}
          </div>

          <AddIngredientModal
            modalId={`recipe_step_modal${index}`}
            onAddIngredientClick={(ingredient) => onAddIngredientClick(ingredient, index)}
          />
        </div>
      ))}
      <div className="flex justify-center my-4">
        <button onClick={onAddStepClick} className="btn">
          Schritt hinzufügen +
        </button>
      </div>
    </div>
  );

  function onAddIngredientClick(ingredientDto: IngredientDto, stepIndex: number) {
    console.log("on ingredient click");
    const ingredient: IngredientInfoDto = {
      ingredientId: ingredientDto.id,
      amount: 0,
      unit: "g",
      title: ingredientDto.title,
    };
    const ingredients = [...steps[stepIndex].ingredients];
    ingredients.push(ingredient);
    steps[stepIndex].ingredients = [...ingredients];
    setSteps([...steps]);
    onRecipeStepChange(steps);
    console.log(steps);
  }

  function onAddStepClick() {
    event?.preventDefault(); //wichtig!

    const step: RecipeStepDto = {
      title: "",
      description: "",
      ingredients: [],
    };
    setSteps(steps.concat([step]));
    onRecipeStepChange(steps); // Hier vielleicht fehler
  }
}

function IngredientInfoComponent({ ingredient }: { ingredient: IngredientInfoDto }) {
  return (
    <div className="border rounded-lg p-4 mb-4">
      <h3 className="font-bold text-secondary">{ingredient.title}</h3>
      <div className="flex flex-row  space-x-4">
        <div className="form-control w-full">
          <label className="label">
            <span className="label-text">Anzahl in Gram oder Milliliter</span>
          </label>
          <input type="text" placeholder="150" className="input input-bordered w-full" />
        </div>
        <div className="form-control w-full">
          <label className="label">
            <span className="label-text">Einheit (ml / g)</span>
          </label>
          <input type="text" defaultValue="g" placeholder="g" className="input input-bordered w-full" />
        </div>
      </div>
    </div>
  );
}
