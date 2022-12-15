"use client";
import { useState } from "react";
import { RecipeFilter } from "../utils/dto/RecipeFilter";

export default function Tab({ onTabChange }: { onTabChange: (x: RecipeFilter) => void }) {
  const [tab, setTab] = useState(0);
  return (
    <div className="tabs tabs-boxed p-2 bg-base-100 flex-col">
      <a className={`tab ${tab == 0 ? "tab-active" : ""}`} onClick={() => handleTabChange(0, RecipeFilter.CHEAP)}>
        Günstig
      </a>
      <a
        className={`tab ${tab == 1 ? "tab-active" : ""}`}
        onClick={() => handleTabChange(1, RecipeFilter.FAST_TO_COOK)}
      >
        Schnell
      </a>
      <a className={`tab ${tab == 2 ? "tab-active" : ""}`} onClick={() => handleTabChange(2, RecipeFilter.POPULAR)}>
        Beliebt
      </a>
    </div>
  );

  function handleTabChange(index: number, filter: RecipeFilter) {
    setTab(index);
    onTabChange(filter);
  }
}
