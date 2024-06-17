<script setup></script>

<template>
  <v-sheet id="boardBox">
    <v-sheet class="boardHeader">
      <v-row>
        <v-col cols="12">
          <v-icon icon="fas fa-bullhorn" />
          &nbsp;
          <v-icon
            v-for="char in 'notice'"
            :key="char"
            :icon="'fas fa-' + char"
          />
        </v-col>
      </v-row>
    </v-sheet>
    <RouterView />
  </v-sheet>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
@import "@v-css/contents/board/boardView.css";
</style>
