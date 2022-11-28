// erster parameter children danach nur noch props
export default function BlueButton({ children, ...props }: any) {
  return (
    <button className="bg-blue-500">
      BlueButton{children}
      <h2>Mit dem Namen: {props.name}</h2>
    </button>
  );
}
