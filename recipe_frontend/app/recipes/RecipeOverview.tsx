import Image from 'next/image'
import foodimage from '/public/food/pizza2.png'
export default function RecipeOverview({ children, ...props }: any) {
        return (
            <div className="card w-96 bg-base-100 shadow-xl pb-50">
                <div className="card-body">
                    <Image className='rounded-2xl'
                        src={foodimage}
                        alt="recipe"
                        width={500}
                        height={500}
                    />
                    <h1 className="card-title">Pizza mit gegrilltem Gemüse</h1>
                    <h2>einfach lecker</h2>
                    <div className="rating gap-1">
                        <input type="radio" name="rating-3" className="mask mask-heart bg-red-400" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-orange-400" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-yellow-400" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-lime-400" />
                        <input type="radio" name="rating-3" className="mask mask-heart bg-green-400" />
                    </div>
                    <p>Preis</p>
                    <p>Zubereitungsdauer</p>
                    <p>Schwierigkeit</p>
                    <div className="card-actions justify-end">
                        <button className="btn btn-primary">Details</button>
                    </div>
                </div>
            </div>
        )
    }