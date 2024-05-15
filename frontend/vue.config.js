const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  // outputDir: npm run build 로 빌드시 파일이 생성되는 위치입니다.
  outputDir: "../src/main/resources/static",
  chainWebpack(config) { //빌드 시 빌드되어 나오는 js파일을 js폴더 아래로 묶어 빌드한다
    config.output.filename("js/[name].js");
  },
  indexPath: "index.html",

  // devServer : SpringBoot 의 내장 WAS 주소
  devServer:{
    proxy:  'http://localhost:8082',
    disableHostCheck: true
  }

});
