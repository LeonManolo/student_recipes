"use client";

import CreateIngredientRequestDto from "../utils/dto/CreateIngredientRequestDto";
import StudentRecipesClient from "../utils/StudentRecipesClient";

export default function CreateIngedrient() {
    return (
        <div className="justify-center">
            <button onClick={() => createIngredient()} className="btn btn-outline btn-accent">Button</button>
            {/* The button to open modal */}
            <a href="#my-modal-2" className="btn">open modal</a>
            <div className="modal" id="my-modal-2">
                <div className="modal-box">
                    <h3 className="font-bold text-lg">Congratulations random Internet user!</h3>
                    <p className="py-4">Youve been selected for a chance to get one year of subscription to use Wikipedia for free!</p>
                    <div className="modal-action">
                        <a href="#" className="btn">Yay!</a>
                    </div>
                </div>
            </div>
        </div >
    );

    async function createIngredient(): Promise<void> {
        console.log("pressed createIngredient")
        const ingredient: CreateIngredientRequestDto = {
            "title": "Kartoffel",
            "calories": 12,
            "protein": 22,
            "carbohydrates": 12,
            "fat": 33
        }
        try {
            const recipeClient = new StudentRecipesClient()
            const success = await recipeClient.createIngredient(ingredient)
            console.log(success)
        } catch (e: any) {
            console.log(e?.message)
        }
    }
}