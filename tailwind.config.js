/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        nocturne: {
          base: "#11131d",
          surface: "#1d1f2a",
          surfaceLow: "#191b26",
          surfaceHigh: "#272935",
          void: "#030508",
        },
        starlight: {
          gold: "#F6C85F",
          goldLight: "#FFE29A",
          goldDim: "#EEC058",
        },
        nebula: {
          cyan: "#38BDF8",
          indigo: "#6366F1",
          lavender: "#C084FC",
        }
      },
      fontFamily: {
        serif: ["'Noto Serif'", "serif"],
        sans: ["'Plus Jakarta Sans'", "sans-serif"],
      }
    },
  },
  plugins: [],
}
