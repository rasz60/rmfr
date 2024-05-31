export default {
  boardListInit() {
    this.pageCnt = Math.floor(this.totalCnt / this.perCnt + 1);
    var testIdx = this.currPage * this.perCnt;

    if (this.totalCnt - testIdx > 0) {
      this.start = this.totalCnt - testIdx + 1;
      this.end = this.totalCnt - testIdx + 10;
      this.listCnt = this.perCnt;
    } else {
      this.start = 1;
      this.end = testIdx - this.totalCnt;
      this.listCnt = this.end - this.start + 1;
    }
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
};
