"use client"
import { useState } from "react"

export default function Tab() {
  const [tab, setTab] = useState(0)
  return (
    <div className="tabs tabs-boxed p-2">
      <a className={`tab ${tab == 0 ? "tab-active" : ""}`} onClick={() => setTab(0)}>Günstig</a>
      <a className={`tab ${tab == 1 ? "tab-active" : ""}`} onClick={() => setTab(1)}>Schnell</a>
      <a className={`tab ${tab == 2 ? "tab-active" : ""}`} onClick={() => setTab(2)}>Beliebt</a>
    </div>
  )
}