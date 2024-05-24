<script setup></script>

<template>
  <div class="boardBody">
    <div id="boardUtils" class="row">
      <div class="col-1">총 {{ totalCnt }}개</div>
      <div class="col-7"></div>
      <div id="utilSearch" class="col-4 row">
        <div class="col-3">
          <select class="form-select form-select-sm">
            <option>제목</option>
            <option>작성자</option>
            <option>작성일</option>
          </select>
        </div>
        <div class="col-7">
          <input type="text" class="form-control form-control-sm" />
        </div>
        <div class="col-2">
          <a class="btn btn-sm btn-light">검색</a>
        </div>
      </div>
    </div>
    <div class="btn btn-lg btn-secondary disabled">
      <span class="col-1">순번</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn btn-lg btn-outline-light text-dark" v-show="totalCnt == 0">
      <span class="col-12">작성된 글이 없습니다.</span>
    </div>

    <div
      v-for="i in listCnt"
      :key="i"
      class="btn btn-lg btn-outline-light text-dark"
    >
      <span class="col-1">{{ end - i + 1 }}</span>
      <span class="col-6">제목</span>
      <span class="col-3">작성자</span>
      <span class="col-2">작성일</span>
    </div>
    <div class="btn-box">
      <a class="btn btn-sm btn-outline-primary" href="/board/notice/item/c">
        <font-awesome-icon :icon="['far', 'pen-to-square']" />
      </a>
    </div>
  </div>

  <nav aria-label="Page navigation" id="pagenation">
    <ul class="pagination">
      <li class="page-item">
        <a class="page-link text-dark" href="#" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li
        class="page-item"
        v-for="i in pageCnt"
        :key="i"
        :id="i"
        @click="pageMove(i)"
      >
        <a class="page-link text-dark" href="#">{{ i }}</a>
      </li>
      <li class="page-item">
        <a class="page-link text-dark" href="#" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      totalCnt: 15,
      perCnt: 10,
      listCnt: 0,
      start: 0,
      end: 0,
      pageCnt: 0,
      currPage: 1,
    };
  },
  mounted() {
    this.boardListInit();
  },
  methods: {
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
  },
};
</script>

<style scoped>
@import "@b-css/contents/board/notice/boardList.css";
</style>
