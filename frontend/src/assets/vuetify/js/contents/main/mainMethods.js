export default {
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
    }
  },
  fnSearchDate() {
    if (this.searchDate != null) {
      var year = this.searchDate.getFullYear();
      var month = this.searchDate.getMonth();
      var date = this.searchDate.getDate();

      this.diff =
        (new Date(year, month, date).getTime() - this.today.getTime()) /
        (24 * 60 * 60 * 1000);

      this.fnSetDate(0);
    }
  },
  fnGoToday() {
    this.diff = 0;
    this.searchDate = null;
    this.today = this.fnSetToday();
    this.fnSetDate(0);
  },
  selectEvent(evt) {
    for (var i in this.events) {
      if (this.events[i] == evt) {
        this.events[i].selected = true;
      } else {
        this.events[i].selected = false;
      }
    }
  },
  fnValue(v) {
    console.log(v);
  },

  fnSetAllowedDates(v, event) {
    console.log(v, event);
  },
};
