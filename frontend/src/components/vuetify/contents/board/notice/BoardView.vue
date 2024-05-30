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

<style>
div#content {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}

div#boardBox {
  width: 95%;
  margin: 0 auto;

  select,
  input,
  textarea {
    box-shadow: inset 0 -1px #ddd;
  }

  div.boardHeader {
    margin-top: 20px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    margin-bottom: 30px;

    h2.title {
      width: 100%;
      text-align: left;
      font-size: 20px;
      border-left: 5px solid rgb(224, 68, 68);
      margin: 0;
    }
  }
}
</style>
