export default function LoadingDots() {
  return (
    <div className="pt-6 flex space-x-2 animate-pulse">
      <div className="w-2 h-2 bg-gray-500 rounded-full"></div>
      <div className="w-2 h-2 bg-gray-500 delay-150 rounded-full"></div>
      <div className="w-2 h-2 bg-gray-500 delay-300 rounded-full"></div>
    </div>
  );
}
