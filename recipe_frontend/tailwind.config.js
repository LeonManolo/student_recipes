/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./app/**/*.{js,ts,jsx,tsx}", // Note the addition of the `app` directory.
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'custom-first': '#A7D2CB',
        'custom-second': '#F2D386',
        'custom-third': '#C98474',
        'custom-forth': '#3C3C3C',
      },
    },
  },
  plugins: [require("daisyui")],
}