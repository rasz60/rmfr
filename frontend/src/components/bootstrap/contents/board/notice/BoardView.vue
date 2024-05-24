<script setup></script>

<template>
  <div id="boardBox">
    <div class="boardHeader">
      <h2 class="display-6 title">
        &nbsp;
        <font-awesome-icon :icon="['fas', 'bullhorn']" />
        &nbsp;
        <font-awesome-icon
          v-for="char in 'notice'"
          :key="char"
          :icon="['fas', char]"
        />
      </h2>
    </div>
    <RouterView />
  </div>
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
