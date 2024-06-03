<script setup></script>

<template>
  <v-sheet class="boardBody">
    <v-form
      @submit.prevent
      id="createItemFrm"
      ref="form"
      method="post"
      action="/board/notice/item/create"
    >
      <v-row class="body-row">
        <v-text-field
          prepend-icon="fas fa-t"
          variant="outlined"
          label="제목(title)"
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
          rows="10"
          label="내용(contents)"
          auto-grow
          :rules="contentsRules"
          name="ancContents"
        ></v-textarea>
      </v-row>
      <v-row id="btn-row" class="body-row">
        <v-btn
          icon="fas fa-save"
          class="board-item-btn"
          @click="fnSave"
        ></v-btn>
        <v-btn icon="fas fa-eraser" class="board-item-btn"></v-btn>
        <v-btn
          icon="fas fa-list"
          class="board-item-btn"
          href="/board/notice"
        ></v-btn>
      </v-row>
    </v-form>
  </v-sheet>

  <!--
  <form id="createItemFrm" method="post" action="/board/notice/item/create">
    <div class="row">
      <div class="col-2">
        <label for="title">제목</label>
      </div>
      <div class="col-10">
        <input
          type="text"
          id="title"
          name="ancTitle"
          class="form-control"
          v-model="title"
          maxlength="300"
          minlength="1"
        />
      </div>
    </div>

    <div class="row">
      <div class="col-2">
        <label for="tags">키워드</label>
      </div>
      <div class="col-10">
        <div id="hashtag" class="form-control">
          <div id="tagItems"></div>
          <div id="tagIpt">
            <input
              type="text"
              id="tags"
              @keydown="fnHashTag($event)"
              placeholder="영문 대소문자, 한글, 숫자, _, -만 사용해주세요."
            />
            <input type="hidden" name="ancKw" />
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-12">
        <textarea
          class="form-control"
          name="ancCotents"
          rows="20"
          maxlength="3000"
          minlength="1"
        ></textarea>
      </div>
    </div>

    <div class="row">
      <div id="btnRow" class="col-12 d-flex justify-content-end">
        <a class="btn btn-sm btn-outline-success" @click="fnSave">
          <font-awesome-icon :icon="['fas', 'floppy-disk']" />
        </a>
        <a class="btn btn-sm btn-outline-danger">
          <font-awesome-icon :icon="['fas', 'eraser']" />
        </a>
        <a class="btn btn-sm btn-outline-secondary" href="/board/notice">
          <font-awesome-icon :icon="['far', 'rectangle-list']" />
        </a>
      </div>
    </div>
  </form>
  -->
</template>

<script>
export default {
  data() {
    return {
      ancTitle: "",
      ancContents: "",
      ancKw: [],
    };
  },
  computed: {
    titleRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        return "제목은 필수 값입니다.";
      };
      rules.push(nullChk);

      const lengthChk = (v) => {
        console.log(v.length);
        if (v.length <= 100) return true;
        return "내용은 100자를 넘길 수 없습니다.";
      };
      rules.push(lengthChk);

      return rules;
    },

    contentsRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        return "내용은 필수 값입니다.";
      };
      rules.push(nullChk);

      return rules;
    },

    keywordRules() {
      const rules = [];

      const regExp = (v) => {
        if (v.length > 0) {
          var chkVal = v[v.length - 1];

          if (chkVal.indexOf("|") < 0) return true;

          v.splice(v.length - 1, 1);
          return "특수문자 '|'를 포함한 키워드를 사용할 수 없습니다.";
        } else {
          return true;
        }
      };
      rules.push(regExp);

      return rules;
    },
  },
  methods: {
    fnHashTag(evt) {
      if (evt.keyCode == 13) {
        console.log(this.chips);

        var val = this.chips[this.chips.length - 1];

        if (val.indexOf("|") > 0) {
          this.chips.splice(this.chips.length - 1, 1);
          alert("특수문자 '|'를 포함한 키워드를 사용할 수 없습니다.");
          return false;
        }

        if (this.chips.length > 10) {
          this.chips.splice(this.chips.length - 1, 1);
          alert("10개 이상의 키워드를 등록할 수 없습니다.");
          return false;
        }
      }
    },
    async fnSave() {
      var chk = await this.validate();

      if (chk) {
        document.querySelector("form#createItemFrm").submit();
      }
    },

    async validate() {
      let chk = await this.$refs.form.validate();

      if (!chk.valid) {
        alert("입력한 값을 다시 확인해주세요.");
      }
      return chk.valid;
    },

    remove(item) {
      this.chips.splice(this.chips.indexOf(item), 1);
    },
  },
};
</script>

<style>
.boardBody {
  margin-top: 50px !important;
  margin: 0 auto;
}

.body-row {
  margin-top: 20px !important;
}

#btn-row {
  display: flex;
  justify-content: flex-end;
  align-items: center;

  .board-item-btn {
    margin-left: 5px;
    margin-right: 5px;
  }
}
</style>
