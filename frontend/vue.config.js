const { defineConfig } = require("@vue/cli-service");
const path = require("path");

module.exports = defineConfig({
  // outputDir: npm run build 로 빌드시 파일이 생성되는 위치입니다.
  outputDir: "../src/main/resources/static",
  chainWebpack: (config) => {
    //빌드 시 빌드되어 나오는 js파일을 js폴더 아래로 묶어 빌드한다
    config.output.filename("js/[name].js");

    //import 시 파일 파일 경로 alias 설정
    config.resolve.alias.set(
      "@b-js",
      path.resolve(__dirname, "src/assets/bootstrap/js/")
    );
    config.resolve.alias.set(
      "@b-css",
      path.resolve(__dirname, "src/assets/bootstrap/css/")
    );
    config.resolve.alias.set(
      "@b-components",
      path.resolve(__dirname, "src/components/bootstrap/")
    );
    config.resolve.alias.set(
      "@b-router",
      path.resolve(__dirname, "src/router/bootstrap/")
    );

    config.resolve.alias.set(
      "@v-js",
      path.resolve(__dirname, "src/assets/vuetify/js/")
    );
    config.resolve.alias.set(
      "@v-css",
      path.resolve(__dirname, "src/assets/vuetify/css/")
    );
    config.resolve.alias.set(
      "@v-components",
      path.resolve(__dirname, "src/components/vuetify/")
    );
    config.resolve.alias.set(
      "@v-router",
      path.resolve(__dirname, "src/router/vuetify/")
    );
  },
  indexPath: "index.html",

  // devServer : SpringBoot 의 내장 WAS 주소
  devServer: {
    proxy: "http://localhost:8082",
    //disableHostCheck: true,
  },
});
