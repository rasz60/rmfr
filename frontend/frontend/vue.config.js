const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  // outputDir: npm run build 로 빌드시 파일이 생성되는 위치입니다.
  outputDir:"../../src/main/resources/static",

  // devServer : SpringBoot 의 내장 WAS 주소
  // devServer:{
  //   prot: 8082,
  //   proxy:  'http://localhost:8082',
  //   disableHostCheck: true
  // },
});