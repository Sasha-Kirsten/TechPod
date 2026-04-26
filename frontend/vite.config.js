// import { defineConfig } from 'vite'
// import react from '@vitejs/plugin-react'
// import tailwindcss from "@tailwindcss/vite";
// import babel from '@rolldown/plugin-babel'
// import {tailwindcss} from 'tailwindcss'

// https://vite.dev/config/
// export default defineConfig({
//   plugins: [
//     react(),
//     babel({ presets: [reactCompilerPreset()] })
//   ],
// })

// export default defineConfig({
//   plugins: [react(),  tailwindcss()],
  // test: {
  //   globals: true,
  //   environment: 'jsdom',
  //   setupFiles: './src/test/setup.js',
  // },
//   server: {
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//       }
//     }
//   }
// })
// import '@testing-library/jest-dom'






import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// Polyfill for 'global' in browser
export default defineConfig({
  plugins: [react()],
  define: {
    global: 'window',
  },
})
