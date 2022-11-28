import BlueButton from "../BlueButton";

// params enthält die Parameter der url bei "/recipes/123" ist params.id = 123, id weil Ordnername [id]
export default async function RecipePage({ params }: any) {
  return <div>Hallo mit der id: {params.id} <BlueButton name="Hallo">
    </BlueButton></div>;
}
