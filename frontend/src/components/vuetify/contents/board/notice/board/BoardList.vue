<script setup>
import boardListDatas from "@v-js/contents/board/boardListDatas.js";
import boardListMethods from "@v-js/contents/board/boardListMethods.js";
import { VDateInput } from "vuetify/labs/VDateInput";
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
              :items="searchType"
              variant="underlined"
              v-model="sType"
            >
            </v-select>
          </v-col>
          <v-col cols="9" v-show="sType != 'ancRegDate'">
            <v-text-field
              variant="underlined"
              label="검색어"
              append-icon="fa-magnifying-glass"
              @click:append="fnBoardSearch"
              v-model="sValue"
            ></v-text-field>
          </v-col>
          <v-col cols="9" v-show="sType == 'ancRegDate'">
            <v-date-input
              id="sDateValue"
              prepend-icon=""
              prepend-inner-icon="far fa-calendar"
              label="검색일"
              variant="underlined"
              multiple="range"
              v-model="sDateValue"
              append-icon="fa-magnifying-glass"
              @click:append="fnBoardSearch"
            ></v-date-input>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-card id="boardList">
      <v-list select-strategy="classic">
        <v-list-item id="boardListTop">
          <v-row>
            <v-col cols="1">순번</v-col>
            <v-col
              v-for="sortItem in sort"
              :key="sortItem"
              :cols="sortItem.wid"
              @click="fnSortOrder(sortItem.idx)"
            >
              {{ sortItem.name }}&nbsp;<v-icon
                :icon="sortItem.icon"
                size="13px"
              ></v-icon>
            </v-col>
          </v-row>
        </v-list-item>
        <v-list-item
          v-for="item in items"
          :key="item"
          class="boardItem"
          @click="fnShowDetails(item.ancUuid)"
          v-show="items.length > 0"
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
        <v-list-item v-show="items.length <= 0">
          <v-row>
            <v-col cols="12">
              <v-icon icon="fas fa-times" color="red"></v-icon>
              게시물이없습니다.
              <v-icon icon="fas fa-times" color="red"></v-icon>
            </v-col>
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
        <v-btn
          icon="far fa-pen-to-square"
          @click="$router.push('/board/notice/item/c')"
          v-show="$loginInfo.mLevel == 2"
        ></v-btn>
      </v-col>
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  data() {
    return boardListDatas;
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
</style>
