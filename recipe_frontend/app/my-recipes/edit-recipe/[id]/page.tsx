"use client";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import AddIngredientModal from "../../../../components/AddIngredientModal";
import CreateRecipeRequestDto, { RecipeStepDto, IngredientInfoDto } from "../../../../utils/dto/CreateRecipeRequestDto";
import IngredientDto from "../../../../utils/dto/IngredientDto";
import StudentRecipesClient, { StudentRecipesClientError } from "../../../../utils/StudentRecipesClient";
import EditRecipeStepsComponent from "./EditRecipeStep";

/* Page to edit data of a recipe that has already been created by user. */
export default function EditRecipePage({ params }: any) {
  const recipeId = params.id;
  const [recipeSteps, setRecipeSteps] = useState<RecipeStepDto[]>([]);
  const [title, setTitle] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [servings, setServings] = useState<number>(0);
  const [cookTime, setCookTime] = useState<number>(0);
  const [price, setPrice] = useState<number>(0);
  const [imageUrl, setImageUrl] = useState<string>();

  const [file, setFile] = useState<File>();
  const [filebase64, setFileBase64] = useState<string>("");
  const router = useRouter();

  /* State hooks to set initial value. */
  useEffect(() => {
    fetchInitialRecipeAndUpdate();
  }, []);

  return (
    <div className="flex flex-row justify-center w-full p-4">
      <form onSubmit={handleSubmit} className="w-2/3 p-4 border border-base-content rounded-lg">
        <h1 className="mb-8">Rezept erstellen</h1>

        <div className="flex flex-row w-full space-x-4">
          <div className="flex items-center w-full aspect-square rounded-lg text-center justify-center bg-base-200">
            {filebase64 === "" && imageUrl !== undefined ? (
              <img className="w-full aspect-square object-cover rounded-lg" alt="recipe image" src={imageUrl} />
            ) : (
              ""
            )}
            {filebase64 && (
              <img className="w-full aspect-square object-cover rounded-lg" alt="recipe image" src={filebase64} />
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
        <EditRecipeStepsComponent
          initialRecipeSteps={recipeSteps}
          onRecipeStepChange={(steps) => {
            console.log("STEP CHANGE!");
            setRecipeSteps([...steps]);
            console.log("STEP ADDED!");
            console.log(steps);
          }}
        />
        <div className="flex flex-col w-full border-opacity-50">
          <div className="divider">Speichern</div>
          <button type="submit" className="btn">
            Rezept Speichern
          </button>
        </div>
      </form>
    </div>
  );

  async function fetchInitialRecipeAndUpdate() {
    try {
      const client = new StudentRecipesClient();
      const recipe = await client.getRecipe(recipeId);
      const initialRecipeSteps: RecipeStepDto[] = recipe.steps.map((step) => {
        return {
          title: step.title,
          description: step.description,
          ingredients: step.ingredients.map<IngredientInfoDto>((i) => {
            return {
              ingredientId: i.ingredient.id,
              title: i.ingredient.title,
              unit: i.unit,
              amount: i.amount,
            };
          }),
        };
      });

      /// TODO

      console.log(recipeSteps);
      setTitle(recipe.title);
      setDescription(recipe.description);
      setServings(recipe.servings);
      setCookTime(recipe.cookTime);
      setPrice(recipe.price);
      setImageUrl(recipe.imageUrl);
      setRecipeSteps([...initialRecipeSteps]);
    } catch (e) {}
  }

  async function handleSubmit() {
    event?.preventDefault();
    console.log("Submit clicked!");
    const recipe: CreateRecipeRequestDto = {
      title: title,
      description: description,
      price: price,
      cookTime: cookTime,
      servings: servings,
      steps: recipeSteps,
    };
    console.log(recipe);

    try {
      if (file === undefined) throw new StudentRecipesClientError("Image missing!");
      const client = new StudentRecipesClient();
      await client.updateRecipe(recipe, recipeId, file);
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
      console.log("This file upload is of type:", fileType);
      const reader = new FileReader();
      reader.readAsBinaryString(fileRef);
      reader.onload = (ev: any) => {
        // convert it to base64
        setFileBase64(`data:${fileType};base64,${btoa(ev.target.result)}`);
      };
    }
  }
}
