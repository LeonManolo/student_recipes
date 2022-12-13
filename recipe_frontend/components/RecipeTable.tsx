import RecipeResponseDto from "../utils/dto/RecipeResponseDto";
import { HiChevronDoubleRight } from "react-icons/hi2";

export default function RecipeTable({ recipe }: { recipe: RecipeResponseDto }) {
    const stepList = recipe.steps.map(step => {
        return step.ingredients.map(ingredientInfo => {
            return <li key={step.stepNumber} className="mb-4">
                <div>{step.title}</div>
                <div className="flex flex-row">
                    <HiChevronDoubleRight size={24} />
                    <div className="mx-1">{ingredientInfo.ingredient.title}</div>
                    <div className="mx-1">{ingredientInfo.amount}</div>
                    <div className="mx-1">{ingredientInfo.unit}</div>
                </div>
                <div>{step.description}</div>
            </li>
        })
    });

    return (
        <div className="card w-full bg-base-100 shadow-xl">
            <div className="card-body">
                <h2 className="card-title">Step by Step Anleitung</h2>
                <ol className="list-decimal">
                    {stepList}
                </ol>
            </div>
        </div>
    )
}