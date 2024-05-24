<script setup>
//import { RouterView } from "vue-router";
import contentDatas from "@v-js/contents/data.js";
import contentMethods from "@v-js/contents/methods.js";
</script>

<template>
  <section id="contents">
    <v-layout id="layout">
      <v-navigation-drawer expand-on-hover rail id="sidebar">
        <v-list density="compact" nav>
          <v-list-item
            v-for="item in menus"
            :key="item"
            :prepend-icon="item.icon"
            :title="item.name"
            :value="item.href"
          >
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-main style="height: 250px">
        <RouterView />
      </v-main>
    </v-layout>
  </section>
</template>

<script>
export default {
  data() {
    return contentDatas;
  },
  methods: contentMethods,
  watch: {
    group() {},
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/contents/content.css";
</style>
