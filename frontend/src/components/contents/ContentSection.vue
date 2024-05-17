<script setup></script>

<template>
  <section id="contents">
    <div id="menu">menu</div>
    <div id="content">content</div>
  </section>
</template>

<script>
export default {};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
section#contents {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 1000px;

  div#menu {
    width: 15%;
  }

  div#content {
    width: 80%;
  }
}
</style>
