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
        <div className="overflow-x-auto">
            <table className="table table-zebra w-full">
                <thead>
                    <tr>
                        <th></th>
                        <th>Zutaten</th>
                        <th>Menge</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>1</th>
                        <td>Crème fraîche</td>
                        <td>200 ml</td>
                    </tr>

                    <tr>
                        <th>2</th>
                        <td>Eier</td>
                        <td>2</td>
                    </tr>

                    <tr>
                        <th>3</th>
                        <td>Mehl</td>
                        <td>350 g</td>
                    </tr>
                    <tr>
                        <th>3</th>
                        <td>Milch</td>
                        <td>500 ml</td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}