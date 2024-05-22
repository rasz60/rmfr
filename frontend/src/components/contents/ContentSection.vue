<script setup>
import { RouterView } from "vue-router";
import contentDatas from "@js/contents/data.js";
import contentMethods from "@js/contents/methods.js";
</script>

<template>
  <section id="contents">
    <div id="menuSlim" class="menuBox">
      <dl class="menu slim">
        <dd v-for="item in menus" :key="item" @click="fn_go_href(item.href)">
          <span class="slimIcon">
            <font-awesome-icon :icon="['fas', item.icon]" />
          </span>
        </dd>
      </dl>
    </div>
    <div id="menuFull" class="menuBox">
      <dl class="menu full">
        <dd v-for="item in menus" :key="item" @click="fn_go_href(item.href)">
          <div class="fullIcon">
            <font-awesome-icon :icon="['fas', item.icon]" />
          </div>
          <div class="fullTxt">
            <span>
              <font-awesome-icon
                v-for="char in Array.from(item.name)"
                :key="char"
                :icon="['fas', char]"
              />
            </span>
          </div>
        </dd>
      </dl>
    </div>
    <div id="content">
      <RouterView />
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return contentDatas;
  },
  methods: contentMethods,
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@css/contents/content.css";
</style>
