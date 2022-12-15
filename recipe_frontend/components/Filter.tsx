"use client"
import { useState } from "react"

/**
 * Component to filter recipes by categories.
 * @returns A dropdown menu of filter.
 */
export default function DropDown() {
    const [tab, setFilter] = useState(0)
    return (
        <div className="tabs tabs-boxed p-2 bg-base-100 flex-col">
            <a className={`tab ${tab == 0 ? "tab-active" : ""}`} onClick={() => setFilter(0)}>Kuchen</a>
            <a className={`tab ${tab == 1 ? "tab-active" : ""}`} onClick={() => setFilter(1)}>Nudeln</a>
            <a className={`tab ${tab == 2 ? "tab-active" : ""}`} onClick={() => setFilter(2)}>Reis</a>
            <a className={`tab ${tab == 3 ? "tab-active" : ""}`} onClick={() => setFilter(3)}>Fleisch</a>
            <a className={`tab ${tab == 4 ? "tab-active" : ""}`} onClick={() => setFilter(4)}>Vegetarisch</a>
        </div>
    )
}