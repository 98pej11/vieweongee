const { defineConfig } = require('@vue/cli-service')
// module.exports = defineConfig({
//   transpileDependencies: true
// })

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: "https://vieweongee.kro.kr:8080/api",
      },
    },
  },
}
