import { ref } from "vue";
export default {
  async getItems(page) {
    this.items = [];
    var param = this.fnSetParams(page);

    await this.axios.get("/rest/board/getItems/" + param).then((res) => {
      let contents = res.data.content;
      let pages = res.data;
      this.page = ref(page);
      this.pageLength = pages.totalPages;
      var totalEle = pages.totalElements;
      this.totalCnt = totalEle;
      contents.forEach((v) => {
        let content = {
          ancUuid: v.ancUuid,
          ancTitle: v.ancTitle,
          ancRegId: v.ancRegId,
          ancRegDate: v.ancRegDate.replace("T", " "),
          ancHits: v.hits,
          ancLikes: v.likes,
          ancComments: v.ancComments.length,
        };
        this.items.push(content);
      });
      this.fnSortList(pages);
    });
  },
  fnShowDetails(ancUuid) {
    this.$router.push("/board/notice/item/d?itemId=" + ancUuid);
  },

  fnSetParams(page) {
    var param = page;

    for (var s in this.sort) {
      var colName = this.sort[s].colName;
      var sort = this.sort[s].order;

      if (sort != null && colName != "hits" && colName != "likes") {
        param += "/" + colName + " " + sort;
        break;
      }
    }

    if (this.sType != "" && this.sValue != "") {
      param += "/" + this.sType + "/" + this.sValue;
    }

    return param;
  },

  fnBoardSearch() {
    var sType = this.sType;

    if (sType == "ancRegDate") {
      if (this.sDateValue == null) {
        alert("검색할 시작 일자와 종료 일자를 선택해주세요.");
        return false;
      } else {
        this.sValue = this.fnSearchDateStr();
      }
    }
    var sValue = this.sValue;
    if (!sType) {
      alert("검색 유형을 입력해주세요.");
      return false;
    }

    if (!sValue) {
      alert("검색어를 입력해주세요.");
      return false;
    }

    this.getItems(1, sType, sValue);
  },

  fnSearchDateStr() {
    var sDate = this.fnDateToStr(this.sDateValue[0]);
    var eDate = this.fnDateToStr(this.sDateValue[this.sDateValue.length - 1]);

    return sDate + "|" + eDate;
  },
  fnDateToStr(date) {
    console.log(date);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    month = month > 9 ? month : "0" + month;

    return year + "" + month + "" + day;
  },
  fnSortOrder(idx) {
    var currOrder = this.sort[idx].order;

    for (var i in this.sort) {
      if (i == idx) {
        if (currOrder == null) {
          this.sort[i].order = "ASC";
          this.sort[i].icon = "fas fa-sort-up";
        } else if (currOrder == "ASC") {
          this.sort[i].order = "DESC";
          this.sort[i].icon = "fas fa-sort-down";
        } else if (currOrder == "DESC") {
          this.sort[i].order = null;
          this.sort[i].icon = "";
        }
      } else {
        this.sort[i].order = null;
        this.sort[i].icon = "";
      }
    }
    this.getItems(this.page);
  },
  fnSortList(pages) {
    var hits = this.sort[3].order;
    var likes = this.sort[4].order;

    if (hits != null) {
      if (hits == "ASC") {
        this.items.sort(function (a, b) {
          return a.ancHits - b.ancHits;
        });
      } else {
        this.items.sort(function (a, b) {
          return b.ancHits - a.ancHits;
        });
      }
    }
    if (likes != null) {
      if (likes == "ASC") {
        this.items.sort(function (a, b) {
          return a.ancLikes - b.ancLikes;
        });
      } else {
        this.items.sort(function (a, b) {
          return b.ancLikes - a.ancLikes;
        });
      }
    }

    this.fnItemsSeq(pages);
  },
  fnItemsSeq(pages) {
    var totalEle = pages.totalElements;
    var perItems = pages.size;
    var currPage = pages.number;
    var startSeq = totalEle - currPage * perItems;

    for (var i in this.items) {
      this.items[i].seq = startSeq - i;
    }
  },
};
