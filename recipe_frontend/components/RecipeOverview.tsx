import img from '../public/food/pizza3.png'
import Image from 'next/image';
/* import CSS from 'csstype';

const h1Styles: CSS.Properties = {
    backgroundColor: 'rgba(255, 0, 0, 0.85',
    position: 'absolute',
    right: 0,
    bottom: '2rem',
    padding: '0.5rem',
    fontFamily: 'sans-serif',
    fontSize: '1.5rem',
    boxShadow: '0 0 10px rgba(0, 0, 0, 0.3)'
}; */

export default function RecipeOverview({ children, ...props }: any) {
    return (
        <div className="card bg-base-50 shadow-xl">
            <div className="card-body">
                <Image className='rounded-2xl' src={img} alt="recipe" width={300} height={300} />
                {/*                 <Image className='rounded-2xl'
                    src={'https://kochkarussell.com/wp-content/uploads/2021/05/Spaghetti-Carbonara-1.jpg'}
                    alt={"recipe"}
                    width={500}
                    height={500}
                /> */}
                <h1 className="card-title">Pizza </h1>
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
                    <button className="btn btn-primary w-1/2">Rezept anzeigen</button>
                </div>
            </div>
        </div>
    )
}