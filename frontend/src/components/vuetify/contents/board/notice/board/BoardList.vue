<script setup>
import boardListData from "@v-js/contents/board/data.js";
import boardListMethods from "@v-js/contents/board/methods.js";
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
            <v-col cols="2" @click="fnSortOrder(2)">작성자</v-col>
            <v-col cols="2" @click="fnSortOrder(3)">작성일</v-col>
            <v-col cols="1" @click="fnSortOrder(4)">조회수</v-col>
            <v-col cols="1" @click="fnSortOrder(5)">좋아요</v-col>
          </v-row>
        </v-list-item>
        <v-list-item
          v-for="item in items"
          :key="item"
          class="boardItem"
          @click="fnShowDetails(item.ancUuid)"
        >
          <v-row>
            <v-col cols="1">{{ item.seq }}</v-col>
            <v-col cols="5" class="title"
              >{{ item.ancTitle }}&nbsp;[{{ item.ancComments }}]</v-col
            >
            <v-col cols="2">{{ item.ancRegId }}</v-col>
            <v-col cols="2">{{ item.ancRegDate }}</v-col>
            <v-col cols="1">{{ item.ancHits }}</v-col>
            <v-col cols="1">{{ item.ancLikes }}</v-col>
          </v-row>
        </v-list-item>
      </v-list>
    </v-card>
    <v-row id="btnRow">
      <v-col cols="1"></v-col>
      <v-col cols="10">
        <v-pagination :length="pageLength" v-model="page"></v-pagination>
      </v-col>
      <v-col cols="1">
        <v-btn icon="far fa-pen-to-square" href="/board/notice/item/c"></v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  data() {
    return boardListData;
  },
  mounted() {
    this.getItems(1);
  },
  methods: boardListMethods,
  watch: {
    page(v) {
      this.getItems(v);
    },
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
    background-color: silver;
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

    .title {
      text-decoration: underline;
    }
  }
}

#btnRow {
  margin-top: 12px;
}
</style>
