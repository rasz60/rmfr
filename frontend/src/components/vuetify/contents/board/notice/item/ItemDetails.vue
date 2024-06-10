<script setup></script>

<template>
  <v-sheet class="boardBody">
    <v-form @submit.prevent id="editItemFrm" ref="form" method="post">
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

      <v-card>
        <v-row class="body-row" :v-show="!editmode">
          <v-col cols="1"></v-col>
          <v-col cols="10">
            <v-textarea
              variant="underlined"
              rows="1"
              auto-grow
              label="댓글"
              name="ancContents"
              v-model="comment"
            ></v-textarea>
          </v-col>
          <v-col cols="1" id="commentBtnCol">
            <v-btn icon="far fa-comment-dots" @click="fnRegComment"></v-btn>
          </v-col>
        </v-row>
        <v-row
          v-show="ancComments.length > 0"
          v-for="comment in ancComments"
          :key="comment"
        >
          <v-col cols="1" class="register"
            >@{{ comment.ancCommenterId.mid }}</v-col
          >
          <v-col cols="10" class="comment">{{ comment.ancComment }}</v-col>
          <v-col cols="1">
            <v-btn
              density="comfortable"
              icon="fas fa-reply"
              class="regSubReply"
              @click="fnRegSubReply($event.target)"
            ></v-btn>
            <v-btn
              density="comfortable"
              icon="fas fa-times"
              class="delSubReply"
            ></v-btn>
          </v-col>
        </v-row>
        <v-row v-show="ancComments.length == 0" id="not-comment">
          아직 작성된 댓글이 없습니다.
        </v-row>
      </v-card>
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
      ancComments: [],
      comment: "",
      ancParentCommentUuid: "",
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
          this.ancComments = jsonData.ancComments;
          console.log(jsonData.ancComments);
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

    async fnRegComment() {
      const comment = {
        ancUuid: this.ancUuid,
        ancParentCommentUuid: this.ancParentCommentUuid,
        ancCommentDepth: 0,
        ancComment: this.comment,
      };

      await this.axios
        .post("/rest/board/item/regComment", JSON.stringify(comment))
        .then((res) => {
          console.log(res.status);
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

    fnRegSubReply(ele) {
      console.log(ele);
      ele.color = "red";
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

#commentBtnCol {
  display: flex;
  justify-content: center;
  align-items: center;

  button {
    margin-top: -20px;
  }
}

#not-comment {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100px;
  text-align: center;
  font-style: italic;
  color: #4e4e4e;
}

.comment {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.register {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 13px;
  font-style: italic;
  cursor: pointer;
}

.register:hover {
  color: darkblue;
  text-decoration: underline;
}

.regSubReply {
  margin-right: 10px;
}

.regSubReply i {
  transform-origin: center;
  transform: rotate(180deg);
}

.regSubReply,
.delSubReply {
  font-size: 0.7em !important;
}
</style>
