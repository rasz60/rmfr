import { ref } from "vue";
export default {
  async getItems(page, searchType, searchValue) {
    this.items = [];

    var param = page;
    if (searchType && searchValue) {
      param += "/" + searchType + "/" + searchValue;
    }

    await this.axios.get("/rest/board/getItems/" + param).then((res) => {
      let contents = res.data.content;
      let pages = res.data;
      this.page = ref(page);
      this.pageLength = pages.totalPages;

      contents.forEach((v, i) => {
        var totalEle = pages.totalElements;
        var perItems = pages.size;
        var currPage = pages.number;

        var startSeq = totalEle - currPage * perItems;
        this.totalCnt = totalEle;
        let content = {
          seq: startSeq - i,
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
    });
  },
  fnShowDetails(ancUuid) {
    this.$router.push("/board/notice/item/d?itemId=" + ancUuid);
  },

  fnBoardSearch() {
    var sType = this.sType;
    var sValue = this.sValue;

    if (sType == "ancRegDate") {
      if (this.sDateValue == null) {
        alert("검색할 시작 일자와 종료 일자를 선택해주세요.");
        return false;
      } else {
        sValue = this.fnSearchDateStr();
      }
    }

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
};
