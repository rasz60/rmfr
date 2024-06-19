<script setup>
import { VCalendar } from "vuetify/labs/VCalendar";
</script>

<template>
  <v-card class="calendar-row">
    <v-row>
      <v-col cols="7">
        <v-calendar ref="calendar" :start="start" :type="type"></v-calendar>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      start: "",
      type: "month",
    };
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.calendar-row {
  padding: 10px;
}
.v-calendar-month__weeknumber {
  color: white;
}
</style>
