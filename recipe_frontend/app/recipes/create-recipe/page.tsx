"use client";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import AddIngredientModal from "../../../components/AddIngredientModal";
import CreateRecipeRequestDto, { IngredientInfoDto, RecipeStepDto } from "../../../utils/dto/CreateRecipeRequestDto";
import IngredientDto from "../../../utils/dto/IngredientDto";
import StudentRecipesClient, { StudentRecipesClientError } from "../../../utils/StudentRecipesClient";

/* Page to create a new recipe. */
export default function CreateRecipePage() {
  let recipeSteps: RecipeStepDto[] = [];
  /* State hooks to set recipe details provided by user. */
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [servings, setServings] = useState<number>(0);
  const [cookTime, setCookTime] = useState<number>(0);
  const [price, setPrice] = useState<number>(0);
  const [loading, setLoading] = useState(false);
  let category = 1;

  const [file, setFile] = useState<File>();
  const [filebase64, setFileBase64] = useState<string>("");
  const router = useRouter();

  return (
    <div className="flex flex-row justify-center w-full p-4">
      <form onSubmit={handleSubmit} className="w-2/3 p-4 border border-base-content rounded-lg">
        <h1 className="mb-8">Rezept erstellen</h1>

        <div className="flex flex-row w-full space-x-4">
          <div className="flex items-center w-full aspect-square rounded-lg text-center justify-center bg-base-200">
            {/* User can upload own picture and will be prompt to do so, if no image has been provided. */}
            {filebase64 ? (
              <img className="w-full aspect-square object-cover rounded-lg" alt="recipe image" src={filebase64} />
            ) : (
              "Kein Bild ausgewählt"
            )}
          </div>
          <div className="flex justify-center form-control w-full">
            <label className="label">
              <span className="label-text">Bild auswählen</span>
            </label>
            <input
              type="file"
              onChange={onFileChange}
              accept="image/png, image/jpeg"
              className="file-input file-input-bordered w-full"
              required={true}
            />
          </div>
        </div>

        <div className="flex flex-row space-x-4">
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Titel des Rezeptes</span>
            </label>
            <input
              value={title}
              minLength={2}
              onChange={(v) => setTitle(v.target.value)}
              type="text"
              placeholder="Nudeln mit Pesto..."
              className="input input-bordered w-full"
              required={true}
            />
          </div>
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Anzahl an Portionen</span>
            </label>
            <input
              value={`${servings ?? 0}`}
              min={1}
              onChange={(v) => setServings(parseInt(v?.target?.value))}
              type="number"
              placeholder="3..."
              className="input input-bordered w-full"
              required={true}
            />
          </div>
        </div>

        <div className="flex flex-row space-x-4">
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Beschreibung</span>
            </label>
            <input
              value={description}
              onChange={(v) => setDescription(v.target.value)}
              type="text"
              placeholder="Beschreibung..."
              className="input input-bordered w-full"
              required={true}
            />
          </div>
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Kategorie</span>
            </label>
            <select onChange={(v) => parseInt(v.target.value)} defaultValue={1} className="select select-bordered">
              <option disabled={false}>
                Kategorie
              </option>
              <option defaultValue={1}>Vegan</option>
              <option defaultValue={2}>Fleisch</option>
              <option defaultValue={3}>Kuchen</option>
              <option defaultValue={4}>Nudeln</option>
              <option defaultValue={5}>Reis</option>
            </select>
          </div>
        </div>

        <div className="flex flex-row space-x-4">
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Kochzeit in Minuten</span>
            </label>
            <input
              value={`${cookTime}`}
              min={1}
              onChange={(v) => setCookTime(parseInt(v.target.value ?? 0))}
              type="number"
              placeholder="5..."
              className="input input-bordered w-full"
              required={true}
            />
          </div>
          <div className="form-control w-full">
            <label className="label">
              <span className="label-text">Preis in Euro</span>
            </label>
            <input
              value={price}
              onChange={(v) => setPrice(parseInt(v.target.value))}
              type="number"
              placeholder="2..."
              className="input input-bordered w-full"
              required={true}
              min={0}
            />
          </div>
        </div>

        <div className="divider" />
        <RecipeStepsComponent
          onRecipeStepChange={(steps) => {
            recipeSteps = [...steps];
          }}
        />
        <div className="flex flex-col w-full border-opacity-50">
          <div className="divider">Speichern</div>
          <button type="submit" className={`btn ${loading ? "loading" : ""}`}>
            Rezept Speichern
          </button>
        </div>
      </form>
    </div>
  );

  async function handleSubmit() {
    event?.preventDefault();
    const recipe: CreateRecipeRequestDto = {
      title: title,
      description: description,
      cookTime: cookTime,
      servings: servings,
      price: price,
      categories: [category],
      steps: recipeSteps,
    };

    try {
      if (file === undefined) throw new StudentRecipesClientError("Image missing!");
      const client = new StudentRecipesClient();
      await client.createRecipeWithImage(recipe, file);
      router.push("/recipes");
    } catch (recipeError: any) {
      if (recipeError instanceof StudentRecipesClientError) {
        const err = recipeError as StudentRecipesClientError;
        alert(`Falsche eingabe: ${err.message}`);
        console.log(err.message);
      } else {
        alert(recipeError);
      }
    }
  }

  function onFileChange(event: React.ChangeEvent<HTMLInputElement>) {
    if (event.target.files) {
      convertFile(event.target.files);
      setFile(event.target.files[0]);
    }
  }

  function convertFile(files: FileList | null) {
    if (files) {
      const fileRef = files[0] || "";
      const fileType: string = fileRef.type || "";
      const reader = new FileReader();
      reader.readAsBinaryString(fileRef);
      reader.onload = (ev: any) => {
        // convert it to base64
        setFileBase64(`data:${fileType};base64,${btoa(ev.target.result)}`);
      };
    }
  }
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
                  steps[index].ingredients[i] = { ...updatedIngredient };
                  const ingredients = [...steps[index].ingredients];
                  steps[index].ingredients = [...ingredients];
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
    const refreshedSteps = steps.map((s) => {
      return { ...s };
    });
    setSteps([...refreshedSteps]);
    onRecipeStepChange(steps);
  }

  function onAddIngredientClick(ingredientDto: IngredientDto, stepIndex: number) {
    const ingredient: IngredientInfoDto = {
      ingredientId: ingredientDto.id,
      amount: 0,
      unit: "g",
      title: ingredientDto.title,
    };
    const ingredients = [...steps[stepIndex].ingredients];
    ingredients.push({ ...ingredient });
    steps[stepIndex].ingredients = [...ingredients];
    setSteps([...steps]);
    onRecipeStepChange(steps);
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
              onIngredientChange(ingredient );
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
