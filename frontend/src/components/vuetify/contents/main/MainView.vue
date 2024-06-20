<script setup></script>

<template>
  <h3 class="card-header">
    <v-icon icon="fas fa-e" size="22px"></v-icon>
    <v-icon icon="fas fa-v" size="22px"></v-icon>
    <v-icon icon="fas fa-e" size="22px"></v-icon>
    <v-icon icon="fas fa-n" size="22px"></v-icon>
    <v-icon icon="fas fa-t" size="22px"></v-icon>
    <v-icon icon="fas fa-s" size="22px"></v-icon>&nbsp;
    <v-btn icon="fas fa-chevron-right" size="22px"></v-btn>
  </h3>
  <v-card class="calendar-row">
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
            <v-chip
              v-for="event in events.filter((e) => e.date === weekday.realDate)"
              :key="event"
              :prepend-icon="event.icon"
              :color="event.color"
              link
            >
              &nbsp;{{ event.type }} Match!
            </v-chip>
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
    return {
      type: "",
      today: null,
      diff: 0,
      weekdays: [
        { name: "s", date: "", color: "red", today: false, realDate: null },
        { name: "m", date: "", color: "black", today: false, realDate: null },
        { name: "t", date: "", color: "black", today: false, realDate: null },
        { name: "w", date: "", color: "black", today: false, realDate: null },
        { name: "t", date: "", color: "black", today: false, realDate: null },
        { name: "f", date: "", color: "black", today: false, realDate: null },
        { name: "s", date: "", color: "blue", today: false, realDate: null },
      ],
      events: [
        {
          type: "diss",
          genre: "hiphop",
          icon: "fas fa-microphone-lines",
          date: new Date(2024, 5, 22).getTime(),
          color: "orange",
        },
        {
          type: "box",
          genre: "fight",
          icon: "fas fa-user-ninja",
          date: new Date(2024, 5, 17).getTime(),
          color: "red",
        },
        {
          type: "judo",
          genre: "fight",
          icon: "fas fa-user-ninja",
          date: new Date(2024, 5, 19).getTime(),
          color: "red",
        },
        {
          type: "cycle",
          genre: "race",
          icon: "fas fa-person-biking",
          date: new Date(2024, 5, 20).getTime(),
          color: "primary",
        },
        {
          type: "judo",
          genre: "fight",
          icon: "fas fa-user-ninja",
          date: new Date(2024, 5, 22).getTime(),
          color: "red",
        },
      ],
    };
  },
  created() {
    this.today = this.fnSetToday();
    this.fnSetDate(0);
  },
  methods: {
    fnSetToday() {
      var today = new Date();

      var year = today.getFullYear();
      var month = today.getMonth();
      var date = today.getDate();

      var bToday = this.today;

      if (bToday != null) {
        var bYear = bToday.getFullYear();
        var bMonth = bToday.getMonth();
        var bDate = bToday.getDate();

        if (bYear == year && bMonth == month && bDate == date) {
          return bToday;
        }
      }

      this.today = new Date(year, month, date);
      return new Date(year, month, date);
    },
    fnSetDate(diff) {
      var days = ["s", "m", "w", "t", "t", "f", "s"];
      var today = this.fnSetToday();

      this.diff = this.diff + diff;

      var midDate = new Date(
        today.getFullYear(),
        today.getMonth(),
        today.getDate()
      );

      midDate.setDate(midDate.getDate() + this.diff);

      for (var i in this.weekdays) {
        var newDate = new Date(
          midDate.getFullYear(),
          midDate.getMonth(),
          midDate.getDate()
        );

        newDate.setDate(newDate.getDate() + (i - 3));

        var year = 0;
        var month = 0;
        var date = 0;
        var day = 0;

        year = newDate.getFullYear();
        month = newDate.getMonth() + 1;
        date = newDate.getDate();
        day = newDate.getDay();
        this.weekdays[i].realDate = new Date(year, month - 1, date).getTime();

        this.weekdays[i].today =
          this.today.getTime() == new Date(year, month - 1, date).getTime();
        this.weekdays[i].name = days[newDate.getDay()];
        this.weekdays[i].color = day == 6 ? "blue" : day == 0 ? "red" : "black";

        this.weekdays[i].date =
          year +
          "-" +
          (month > 9 ? month : "0" + month) +
          "-" +
          (date > 9 ? date : "0" + date);

        console.log(this.weekdays[i].date);
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.card-header {
  padding: 20px;
  display: flex;
  align-items: center;

  button {
    padding: 0;
    font-size: 10px;
  }
}

.today {
  background-color: aliceblue;
}

.calendar-row {
  margin: 10px;
  padding: 30px;
}

.event-btn-cols {
  display: flex;
  align-items: center;
  justify-content: center;
}

.v-chip {
  width: 95%;
}

.header-txt {
  font-size: 15px;

  hr {
    margin: 8px;
  }
}

.dateStr {
  font-size: 18px;
}

.color-red {
  color: red;
}

.color-blue {
  color: blue;
}
</style>
