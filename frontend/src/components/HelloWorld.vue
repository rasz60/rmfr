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
export default {};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
