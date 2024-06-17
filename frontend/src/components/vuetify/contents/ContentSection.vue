<script setup>
import contentDatas from "@v-js/contents/contentDatas.js";
import contentMethods from "@v-js/contents/contentMethods.js";
</script>

<template>
  <section id="contents">
    <v-layout id="layout">
      <v-navigation-drawer id="sidebar" :rail="$menuDrawer.value">
        <v-list density="compact" nav>
          <v-list-item
            class="menuItem"
            v-for="item in menus"
            :key="item"
            :prepend-icon="item.icon"
            @click="fn_go_href('/board/notice')"
          >
            <v-icon
              class="menuIcon"
              v-for="char in item.name"
              :key="char"
              :icon="'fas fa-' + char"
              v-show="!$menuDrawer.value"
            ></v-icon>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-main>
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
