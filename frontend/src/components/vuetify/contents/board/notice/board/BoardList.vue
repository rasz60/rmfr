<script setup>
import { ref } from "vue";

const page = ref(1);
const items = Array.from({ length: 15 }, (k, v) => ({
  seq: 15 - v,
  title: "제목" + (15 - v),
  regId: "작성자" + (15 - v),
  regDate: new Date().toLocaleDateString(),
  hits: Math.ceil(Math.random() * 10 + 1),
}));
const pageLength =
  items.length % 10 > 0 ? items.length / 10 + 1 : items.length / 10;
</script>

<template>
  <v-sheet class="boardBody">
    <v-row id="boardUtils" no-gutters>
      <v-col cols="1" id="totalCnt">총 {{ totalCnt }}개</v-col>
      <v-col cols="7"></v-col>
      <v-col cols="4">
        <v-row>
          <v-col cols="3">
            <v-select
              label="검색유형"
              :items="['제목', '작성자', '작성일']"
              variant="underlined"
            >
            </v-select>
          </v-col>
          <v-col cols="9">
            <v-text-field
              variant="underlined"
              label="검색어"
              append-icon="fa-magnifying-glass"
              @click:append-icon="fnBoardSearch"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-card id="boardList">
      <v-list select-strategy="classic">
        <v-list-item id="boardListTop">
          <v-row>
            <v-col cols="1" @click="fnSortOrder(0)">순번</v-col>
            <v-col cols="5" @click="fnSortOrder(1)">제목</v-col>
            <v-col cols="3" @click="fnSortOrder(2)">작성자</v-col>
            <v-col cols="2" @click="fnSortOrder(3)">작성일</v-col>
            <v-col cols="1" @click="fnSortOrder(4)">조회수</v-col>
          </v-row>
        </v-list-item>
        <v-list-item
          v-for="item in items"
          :key="item"
          class="boardItem"
          @click="fnShowDetails"
        >
          <v-row>
            <v-col cols="1">{{ item.seq }}</v-col>
            <v-col cols="5">{{ item.title }}</v-col>
            <v-col cols="3">{{ item.regId }}</v-col>
            <v-col cols="2">{{ item.regDate }}</v-col>
            <v-col cols="1">{{ item.hits }}</v-col>
          </v-row>
        </v-list-item>
      </v-list>
    </v-card>
    <v-pagination :length="pageLength" v-model="page"></v-pagination>
  </v-sheet>
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
@import "@v-css/contents/board/notice/boardList.css";

#totalCnt {
  display: flex;
  justify-content: center;
  align-items: center;
}

#boardList {
  padding: 15px;

  #boardListTop {
    background-color: darkgray;
    color: white;
    border-radius: 5px 5px 0 0 !important;
    font-weight: 500;
  }
  .boardItem {
    font-size: 15px;
    font-weight: 400;
    cursor: pointer;
    padding: 15px;
  }

  .boardItem:hover {
    background-color: #f6f6f6;
    font-weight: 500;
  }
}
</style>
