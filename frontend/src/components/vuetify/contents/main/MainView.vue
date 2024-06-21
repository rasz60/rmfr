<script setup>
import { VDateInput } from "vuetify/labs/VDateInput";
import mainDatas from "@v-js/contents/main/mainDatas.js";
import mainMethods from "@v-js/contents/main/mainMethods.js";
</script>

<template>
  <h3 class="card-header">
    <v-icon icon="fas fa-m" size="22px"></v-icon>
    <v-icon icon="fas fa-a" size="22px"></v-icon>
    <v-icon icon="fas fa-t" size="22px"></v-icon>
    <v-icon icon="fas fa-c" size="22px"></v-icon>
    <v-icon icon="fas fa-h" size="22px"></v-icon>
    <v-icon icon="fas fa-e" size="22px"></v-icon>
    <v-icon icon="fas fa-s" size="22px"></v-icon>&nbsp;
    <v-btn icon="fas fa-chevron-right" size="22px"></v-btn>
  </h3>
  <v-card class="calendar-row">
    <v-row>
      <v-col cols="4">
        <v-date-input
          variant="solo"
          prepend-icon=""
          prepend-inner-icon="fa-magnifying-glass"
          v-model="searchDate"
          append-inner-icon="fa-clock-rotate-left"
          @click:append-inner="fnGoToday"
        ></v-date-input>
      </v-col>
    </v-row>
    <v-row>
      <v-col sm="1" class="event-btn-cols">
        <v-btn icon="fas fa-chevron-left" @click="fnSetDate(-7)"></v-btn>
      </v-col>
      <v-col sm="10">
        <v-row>
          <v-col
            v-for="weekday in weekdays"
            :key="weekday"
            :class="
              'color-' +
              weekday.color +
              ' header-txt ' +
              (weekday.today ? 'today' : '')
            "
          >
            <v-icon :icon="'fas fa-' + weekday.name" size="16px"></v-icon>
            <v-divider></v-divider>
            <span class="dateStr">{{ weekday.date }}</span>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row>
          <v-col
            v-for="weekday in weekdays"
            :key="weekday"
            :class="
              'color-' +
              weekday.color +
              ' header-txt ' +
              (weekday.today ? 'today' : '')
            "
          >
            <v-list bg-color="transparent">
              <v-list-item
                class="event-item"
                v-for="event in events.filter(
                  (e) => e.date === weekday.realDate
                )"
                :key="event"
              >
                <v-dialog
                  v-model="event.selected"
                  persistent
                  transition="dialog-bottom-transition"
                  full-width
                >
                  <template v-slot:activator="{ props: activatorProps }">
                    <v-chip
                      :prepend-icon="event.icon"
                      :color="event.color"
                      link
                      v-bind="activatorProps"
                    >
                      &nbsp;{{ event.type }} Match!
                    </v-chip>
                  </template>

                  <v-card>
                    <v-toolbar>
                      &nbsp;&nbsp;
                      <v-icon :icon="event.icon"></v-icon>
                      <v-toolbar-title>{{ event.type }}</v-toolbar-title>
                      <v-spacer></v-spacer>

                      <v-toolbar-items>
                        <v-btn
                          icon="fa-times"
                          @click="event.selected = false"
                          color="red"
                        ></v-btn>
                      </v-toolbar-items>
                    </v-toolbar>

                    <v-list lines="two" subheader>
                      <v-list-subheader>Match Info</v-list-subheader>
                      <v-row>
                        <v-col cols="4">
                          <v-list-item title="Participiant" link>
                            <v-chip
                              v-for="participiant in event.participiants"
                              :key="participiant"
                              link
                              width="auto"
                              >@{{ participiant }}
                            </v-chip>
                          </v-list-item>
                          <v-list-item>
                            <v-list-item title="Schedules" link>
                              <v-date-picker
                                class="dialogCalendar"
                                v-model="event.realDate"
                                @update:modelValue="fnValue"
                              ></v-date-picker>
                            </v-list-item>
                          </v-list-item>
                        </v-col>
                        <v-col cols="8"></v-col>
                      </v-row>
                    </v-list>
                  </v-card>
                </v-dialog>
              </v-list-item>
            </v-list>
          </v-col>
        </v-row>
      </v-col>
      <v-col sm="1" class="event-btn-cols">
        <v-btn icon="fas fa-chevron-right" @click="fnSetDate(7)"></v-btn>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
//import { useDate } from "vuetify";

export default {
  data() {
    return mainDatas;
  },
  created() {
    this.fnGoToday();
  },
  watch: {
    searchDate() {
      this.fnSearchDate();
    },
    events: {
      handler(newValue, oldValue) {
        console.log(newValue, oldValue);
      },
    },
  },
  methods: mainMethods,
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import "@v-css/contents/main/main.css";
</style>
