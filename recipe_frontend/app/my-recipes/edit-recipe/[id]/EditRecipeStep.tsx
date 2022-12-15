"use client";
import { useState, useEffect } from "react";
import AddIngredientModal from "../../../../components/AddIngredientModal";
import { RecipeStepDto, IngredientInfoDto } from "../../../../utils/dto/CreateRecipeRequestDto";
import IngredientDto from "../../../../utils/dto/IngredientDto";

export default function EditRecipeStepsComponent({
  onRecipeStepChange,
  initialRecipeSteps,
}: {
  onRecipeStepChange: (steps: RecipeStepDto[]) => void;
  initialRecipeSteps: RecipeStepDto[];
}) {
  console.log("Das sind die inital steps " + JSON.stringify(initialRecipeSteps));
  const [steps, setSteps] = useState<RecipeStepDto[]>(initialRecipeSteps);

  useEffect(() => setSteps(initialRecipeSteps), [initialRecipeSteps]);

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
              <input
                value={steps[index].title}
                onChange={(v) => {
                  const title = v.target.value;
                  steps[index].title = title;
                  updateSteps();
                }}
                type="text"
                placeholder="Reis waschen..."
                className="input input-bordered w-full"
                required={true}
              />
            </div>
            <div className="form-control w-full">
              <label className="label">
                <span className="label-text">Beschreibung</span>
              </label>
              <input
                value={steps[index].description}
                onChange={(v) => {
                  steps[index].description = v.target.value;
                  updateSteps();
                }}
                type="text"
                placeholder="Reis sorgfältig für etwa 2 Minuten waschen..."
                className="input input-bordered w-full"
                required={true}
              />
            </div>
          </div>

          <div>
            <div className="flex flex-col w-full border-opacity-50">
              <div className="divider">Zutaten</div>
            </div>
            {recipeStep.ingredients.map((ingredient, i) => (
              <IngredientInfoComponent
                key={`step_${index}_ingr_${i}`}
                ingredient={ingredient}
                onIngredientChange={(updatedIngredient) => {
                  steps[i].ingredients[i] = updatedIngredient;
                  const ingredients = [...steps[i].ingredients];
                  steps[i].ingredients = [...ingredients];
                  updateSteps();
                }}
              />
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

  function updateSteps() {
    setSteps([...steps]);
    onRecipeStepChange(steps);
  }

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

function IngredientInfoComponent({
  ingredient,
  onIngredientChange,
}: {
  ingredient: IngredientInfoDto;
  onIngredientChange: (e: IngredientInfoDto) => void;
}) {
  return (
    <div className="border rounded-lg p-4 mb-4">
      <h3 className="font-bold text-secondary">{ingredient.title}</h3>
      <div className="flex flex-row  space-x-4">
        <div className="form-control w-full">
          <label className="label">
            <span className="label-text">Anzahl in Gram oder Milliliter</span>
          </label>
          <input
            value={ingredient.amount}
            min={1}
            onChange={(v) => {
              ingredient.amount = parseInt(v.target.value ?? 0);
              onIngredientChange(ingredient);
            }}
            type="number"
            placeholder="150"
            className="input input-bordered w-full"
            required={true}
          />
        </div>
        <div className="form-control w-full">
          <label className="label">
            <span className="label-text">Einheit (ml / g)</span>
          </label>
          <input
            value={ingredient.unit}
            onChange={(v) => {
              ingredient.unit = v.target.value;
              onIngredientChange(ingredient);
            }}
            type="text"
            placeholder="g"
            className="input input-bordered w-full"
            required={true}
          />
        </div>
      </div>
    </div>
  );
}

function parseIntElseZero(value: any): number {
  const value2 = Number(value?.target?.value ?? 0);
  return isNaN(value2) || value2 === undefined || value?.target?.value === "" ? 0 : value;
}
