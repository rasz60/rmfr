<script setup></script>

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
        <v-btn icon="fas fa-eraser" class="board-item-btn"></v-btn>
        <v-btn
          icon="fas fa-list"
          class="board-item-btn"
          href="/board/notice"
        ></v-btn>
      </v-row>
    </v-form>
  </v-sheet>
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
          alert("특수문자 '|'를 포함한 키워드를 사용할 수 없습니다.");
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
    async fnSave() {
      var chk = await this.validate();

      if (chk) {
        const content = {
          ancTitle: this.ancTitle,
          ancContents: this.ancContents,
          ancKw: this.ancKw,
        };

        await this.axios
          .post("/api/board/notice/item/create", JSON.stringify(content))
          .then((res) => {
            var rst = res.data;

            if (rst == "200") {
              alert("게시글 저장이 완료되었습니다.");
              location.href = "/board/notice";
            } else {
              alert("게시글 저장에 실패하였습니다. 관리자에게 문의해주세요.");
              return false;
            }
          });
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
