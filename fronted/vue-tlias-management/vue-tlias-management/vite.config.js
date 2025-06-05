import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        secure: false,
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    }
  }

})

// import { fileURLToPath, URL } from 'node:url'
// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'

// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [
//     vue({
//       template: {
//         compilerOptions: {
//           isCustomElement: (tag) => tag.includes('-')
//         }
//       }
//     })
//   ],
//   resolve: {
//     alias: {
//       '@': fileURLToPath(new URL('./src', import.meta.url))
//     }
//   },
//   define: {
//     __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'true'
//   },
//   server: {
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         secure: false,
//         changeOrigin: true,
//         rewrite: (path) => path.replace(/^\/api/, ''),
//       }
//     }
//   }
// })
