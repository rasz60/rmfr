<script setup>
import HeaderSection from "@v-components/header/HeaderSection.vue";
import ContentSection from "@v-components/contents/ContentSection.vue";
</script>

<template>
  <v-app>
    <v-container fluid>
      <HeaderSection />

      <ContentSection />
    </v-container>
  </v-app>
</template>

<script>
export default {
  created() {
    document.title = "rmfr"; // 현재 라우트 이름으로 페이지 타이틀을 설정합니다.
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
input {
  outline: none !important;
}
</style>
