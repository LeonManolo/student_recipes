import Link from "next/link";

export default function PostNotFound() {
    return (
        <div className="flex justify-center align-middle">
            <div className="flex flex-col">
                <h1>Ups! Das Rezept scheint ein Mythos zu sein.</h1>
                <Link className="rounded bg-custom-first hover:bg-custom-second" href="/">
                    <a>
                        Zurück nach Hause
                    </a>
                </Link>
            </div>
        </div>
    )
}