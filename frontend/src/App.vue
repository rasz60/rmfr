<template>
  <div>
    <!-- header 영역 -->
    <HeaderComponent />

    <div id="main">
      <div id="sideBar">
        <!-- sidebar 영역 -->
        <SideBar />
      </div>
      <div id="contentDiv">
        <!-- content 영역 -->
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import HeaderComponent from "@/components/layout/HeaderComponent.vue";
import SideBar from "@/components/layout/SideBar.vue";

export default {
  name: "App",
  components: {
    HeaderComponent, SideBar,
  },
};
</script>

<style>
@import "@/assets/css/contents/header/header.css";

</style>