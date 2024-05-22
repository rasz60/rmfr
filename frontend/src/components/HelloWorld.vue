<script setup>
import HeaderSection from "../components/header/HeaderSection.vue";
import ContentSection from "../components/contents/ContentSection.vue";
</script>

<template>
  <section class="container-fluid">
    <HeaderSection />

    <ContentSection />

    <section id="footer">
      <div>footer</div>
    </section>
  </section>
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
