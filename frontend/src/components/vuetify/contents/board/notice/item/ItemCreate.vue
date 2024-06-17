<script setup>
import itemCreateRules from "@v-js/contents/board/item/create/itemCreateRules.js";
import itemCreateMethods from "@v-js/contents/board/item/create/itemCreateMethods.js";
import itemCreateDatas from "@v-js/contents/board/item/create/itemCreateDatas.js";
</script>

<template>
  <v-sheet class="boardBody">
    <v-form @submit.prevent id="createItemFrm" ref="form" method="post">
      <v-row class="body-row">
        <v-text-field
          prepend-icon="fas fa-t"
          variant="outlined"
          label="제목(title)"
          v-model="ancTitle"
          :rules="titleRules"
          name="ancTitle"
        ></v-text-field>
      </v-row>
      <v-row class="body-row">
        <v-combobox
          v-model="ancKw"
          :items="ancKw"
          label="키워드(keywords)"
          prepend-icon="fas fa-filter"
          variant="outlined"
          chips
          clearable
          multiple
          :rules="keywordRules"
          name="ancKw"
        >
          <template v-slot:selection="{ attrs, item, select, selected }">
            <v-chip
              v-bind="attrs"
              :model-value="selected"
              closable
              @click="select"
              @click:close="remove(item)"
            >
              <strong>{{ item }}</strong
              >&nbsp;
              <span>(interest)</span>
            </v-chip>
          </template>
        </v-combobox>
      </v-row>
      <v-row class="body-row">
        <v-textarea
          prepend-icon="fas fa-comments"
          variant="outlined"
          rows="15"
          label="내용(contents)"
          auto-grow
          :rules="contentsRules"
          name="ancContents"
          v-model="ancContents"
        ></v-textarea>
      </v-row>
      <v-row id="btn-row" class="body-row">
        <v-btn
          icon="fas fa-save"
          class="board-item-btn"
          @click="fnSave"
        ></v-btn>
        <v-btn
          icon="fas fa-list"
          class="board-item-btn"
          @click="$router.push('/board/notice')"
        ></v-btn>
      </v-row>
    </v-form>
  </v-sheet>
</template>

<script>
export default {
  data() {
    return itemCreateDatas;
  },
  computed: itemCreateRules,
  methods: itemCreateMethods,
};
</script>

<style>
@import "@v-css/contents/board/notice/item/itemCreate.css";
</style>
