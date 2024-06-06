import { ref } from "vue";
export default {
  async getItems(page) {
    this.items = [];

    await this.axios.get("/rest/board/getItems/" + page).then((res) => {
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
        };
        this.items.push(content);
      });
    });
  },
  fnSetItmes() {
    console.log("");
  },
  pageMove(idx) {
    this.currPage = idx;
    //this.selectPage(idx);
    this.boardListInit();
  },
  selectPage() {},
  fnSortOrder(order) {
    var sort = this.sort[order].order;
    this.sort[order].order =
      sort == null ? "asc" : sort == "asc" ? "desc" : null;
  },
  fnShowDetails(ancUuid) {
    location.href = "/board/notice/item/d?itemId=" + ancUuid;
  },
};
