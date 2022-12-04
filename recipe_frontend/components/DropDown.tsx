export default function DropDown({ children, ...props }: any) {
    return (
        <div className="dropdown dropdown-hover">
            <label tabIndex={0} className="btn m-1 bg-custom-second">Kategorie</label>
            <ul tabIndex={0} className="dropdown-content menu p-2 shadow bg-base-100 rounded-box w-52">
                <li><a>Kuchen</a></li>
                <li><a>Nudeln</a></li>
                <li><a>Reis</a></li>
                <li><a>Fleisch</a></li>
                <li><a>Vegetarisch</a></li>
            </ul>
        </div>
    )
}