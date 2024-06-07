<script setup></script>

<template>
  <v-sheet class="boardBody">
    <v-form @submit.prevent id="editItemFrm" ref="form" method="post">
      <v-row class="body-row">
        <v-text-field
          :prepend-icon="editmode ? 'fas fa-t' : ''"
          :variant="editmode ? 'outlined' : 'solo'"
          label="제목(title)"
          v-model="ancTitle"
          :rules="titleRules"
          name="ancTitle"
          :readonly="!editmode"
        ></v-text-field>
      </v-row>
      <v-row class="body-row">
        <v-combobox
          v-model="ancKw"
          :items="ancKw"
          label="키워드(keywords)"
          :prepend-icon="editmode ? 'fas fa-filter' : ''"
          :variant="editmode ? 'outlined' : 'solo'"
          chips
          :clearable="editmode"
          :multiple="editmode"
          :rules="keywordRules"
          name="ancKw"
          :readonly="!editmode"
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
          :prepend-icon="editmode ? 'fas fa-comments' : ''"
          :variant="editmode ? 'outlined' : 'solo'"
          rows="15"
          label="내용(contents)"
          auto-grow
          :rules="contentsRules"
          name="ancContents"
          v-model="ancContents"
          :readonly="!editmode"
        ></v-textarea>
      </v-row>
      <v-row id="btn-row" class="body-row">
        <v-btn
          :icon="likeItem ? 'fas fa-heart' : 'far fa-heart'"
          class="board-item-btn"
          @click="fnLikes"
          v-show="!editmode"
          :color="likeItem ? 'red' : ''"
        ></v-btn>
        <v-btn
          icon="fas fa-save"
          class="board-item-btn"
          @click="fnSave"
          v-show="editmode"
        ></v-btn>
        <v-btn
          icon="fas fa-wrench"
          class="board-item-btn"
          v-show="editable && !editmode"
          @click.stop="editmode = !editmode"
        ></v-btn>
        <v-btn
          icon="fas fa-eraser"
          v-show="deletable"
          class="board-item-btn"
          @click="fnDeleteItem"
        ></v-btn>
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
      editmode: false,
      editable: false,
      deletable: false,
      likeItem: false,
      ancUuid: "",
      ancTitle: "",
      ancContents: "",
      ancKw: [],
    };
  },

  mounted() {
    this.getItemDetails();
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
    async getItemDetails() {
      var url = location.href;
      var query = url.substring(url.indexOf("?"));
      var param = new URLSearchParams(query);

      await this.axios
        .get("/rest/board/item/d/" + param.get("itemId"))
        .then((res) => {
          let jsonData = res.data;
          this.ancUuid = jsonData.ancUuid;
          this.ancTitle = jsonData.ancTitle;
          this.ancContents = jsonData.ancContents;
          if (jsonData.ancKw != null || jsonData.ancKw != "") {
            var kw = jsonData.ancKw.split("|");
            kw.splice(kw.length - 1, 1);
            this.ancKw = kw;
          }
          this.editable = jsonData.editable;
          this.deletable = jsonData.deletable;
          this.likeItem = jsonData.likeItem;
        });
    },

    async fnSave() {
      var chk = await this.validate();

      if (chk) {
        const content = {
          ancUuid: this.ancUuid,
          ancTitle: this.ancTitle,
          ancContents: this.ancContents,
          ancKw: this.ancKw,
        };

        await this.axios
          .post("/api/board/notice/item/update", JSON.stringify(content))
          .then((res) => {
            var rst = res.data;

            if (rst == "200") {
              alert("게시글 수정이 완료되었습니다.");
              location.href = "/board/notice";
            } else {
              alert("게시글 저장에 실패하였습니다. 관리자에게 문의해주세요.");
              return false;
            }
          });
      }
    },

    async fnLikes() {
      this.likeItem = !this.likeItem;
      await this.axios
        .get("/rest/board/item/likes/" + this.ancUuid + "/" + this.likeItem)
        .then((res) => {
          console.log(res.data);
        });
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

    async fnDeleteItem() {
      if (confirm("게시물을 삭제할까요?")) {
        await this.axios
          .get("/api/board/notice/item/delete/" + this.ancUuid)
          .then((res) => {
            if (res.status == "200") {
              alert("게시물 삭제가 완료되었습니다.");
              location.href = "/board/notice";
            } else {
              alert("게시물 삭제에 실패했습니다. 관리자에게 문의해주세요.");
            }
          });
      } else {
        return false;
      }
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
