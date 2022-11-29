import Link from "next/link";

export default async function ProductWithId({ params }: any) {
  const id = params.id;
  const product = await fetchProducts(id);

  return (
    <div>
      <h1>
        Du befindest dich in der detail seite<br></br>
        {id} {product.description}
      </h1>
      <Link href="/products/22">
        <button>Gib mir das Rezept mit der Id 22</button>
      </Link>
      <div className="grid grid-flow-col gap-5 text-center auto-cols-max">
        <div className="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
          <span className="countdown font-mono text-5xl">
            <span></span>
          </span>
          days
        </div>
        <div className="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
          <span className="countdown font-mono text-5xl">
            <span></span>
          </span>
          hours
        </div>
        <div className="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
          <span className="countdown font-mono text-5xl">
            <span></span>
          </span>
          min
        </div>
        <div className="flex flex-col p-2 bg-neutral rounded-box text-neutral-content">
          <span className="countdown font-mono text-5xl">
            <span></span>
          </span>
          sec
        </div>
      </div>
    </div>
  );
}

interface Products {
  products: Product[];
  total: number;
  skip: number;
  limit: number;
}

interface Product {
  id: number;
  title: string;
  description: string;
}

async function fetchProducts(id: string): Promise<Product> {
  const result = await fetch(`https://dummyjson.com/products/${id}`);
  const json: Product = await result.json();

  return json;
}
