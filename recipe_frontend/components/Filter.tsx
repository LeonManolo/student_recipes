"use client";
import { useState } from "react";

/**
 * Component to filter recipes by categories.
 * @returns A dropdown menu of filter.
 */
export default function DropDown({ onCategoryChange }: { onCategoryChange: (x: number) => void }) {
  const [tab, setFilter] = useState(0);

  return (
    <div className="tabs tabs-boxed p-2 bg-base-100 flex-col items-start text-left">
      <a className={`tab ${tab == 1 ? "tab-active" : ""}`} onClick={() => onChange(1)}>
        Vegan
      </a>
      <a className={`tab ${tab == 2 ? "tab-active" : ""}`} onClick={() => onChange(2)}>
        Fleisch
      </a>
      <a className={`tab ${tab == 3 ? "tab-active" : ""}`} onClick={() => onChange(3)}>
        Kuchen
      </a>
      <a className={`tab ${tab == 4 ? "tab-active" : ""}`} onClick={() => onChange(4)}>
        Nudeln
      </a>
      <a className={`tab ${tab == 5 ? "tab-active" : ""}`} onClick={() => onChange(5)}>
        Reis
      </a>
    </div>
  );

  function onChange(category: number) {
    setFilter(category);
    onCategoryChange(category);
  }
}
