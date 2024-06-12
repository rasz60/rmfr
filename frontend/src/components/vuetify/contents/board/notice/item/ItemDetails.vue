<script setup></script>

<template>
  <v-sheet class="boardBody">
    <v-form @submit.prevent id="editItemFrm" ref="form" method="post">
      <v-row id="btn-row" class="body-row">
        <v-btn
          :icon="likeItem ? 'fas fa-heart' : 'far fa-heart'"
          class="board-item-btn"
          @click="fnLikes"
          v-show="commentable"
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
          @click="$router.push('/board/notice')"
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
        <v-row
          id="commentEditor"
          class="body-row"
          :v-show="!editmode && commentable"
        >
          <v-col cols="2" class="mainRegister">
            <v-chip
              v-show="newComment.commentTarget != ''"
              @click.stop="newComment.commentTarget = ''"
              color="teal"
            >
              {{ newComment.commentTarget }}님에게
            </v-chip>
          </v-col>
          <v-col cols="9">
            <v-textarea
              variant="outlined"
              rows="1"
              auto-grow
              :label="
                commentable ? '댓글쓰기' : '로그인하여 댓글을 남겨주세요.'
              "
              :readonly="!commentable"
              name="ancContents"
              v-model="newComment.comment"
              :rules="commentsRules"
              id="comment"
            ></v-textarea>
          </v-col>
          <v-col cols="1" class="commentBtnRow mainComment">
            <v-btn
              icon="far fa-comment-dots"
              @click="fnRegComment"
              v-show="commentable"
            ></v-btn>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row
          v-show="ancComments.length > 0"
          v-for="comment in ancComments"
          :key="comment"
        >
          <v-col cols="2" class="register"
            >@{{ comment.ancCommenterId.mid }}</v-col
          >
          <v-col
            cols="9"
            :class="
              'comment pad_' +
              comment.ancCommentDepth +
              (comment.ancCommentState != 0 ? ' italic' : '')
            "
          >
            <v-icon
              icon="fas fa-reply"
              class="replyIcon"
              size="12px"
              v-show="comment.ancCommentDepth > 0"
            ></v-icon
            >&nbsp;
            {{ fnCommentText(comment) }}
          </v-col>
          <v-col cols="1" class="commentBtnRow">
            <v-btn
              density="comfortable"
              icon="fas fa-reply"
              class="regSubReply"
              v-show="comment.ancCommentState == 0 && commentable"
              @click="fnRegSubReply(comment)"
            ></v-btn>
            <!-- 로그인 유저와 같으면 보이지 않게 함 -->
            <v-btn
              density="comfortable"
              icon="fas fa-times"
              class="delSubReply"
              v-show="comment.ancCommentState == 0 && comment.commentEditable"
              @click="fnDelSubReply(comment.ancCommentUuid)"
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
      commentable: false,
      ancUuid: "",
      ancTitle: "",
      ancContents: "",
      ancKw: [],
      ancComments: [],
      commentRulesFlag: false,
      newComment: {
        comment: "",
        commentTarget: "",
        ancParentCommentUuid: "",
        depth: 0,
      },
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
        if (v.length <= 1000) return true;
        return "내용은 1000자를 넘길 수 없습니다.";
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
    commentsRules() {
      const rules = [];

      const nullChk = (v) => {
        if (v) return true;
        return "댓글 내용을 입력해주세요.";
      };

      if (this.commentRulesFlag) rules.push(nullChk);

      const lengthChk = (v) => {
        if (v.length <= 200) return true;
        return "댓글은 200자를 넘길 수 없습니다.";
      };
      if (this.commentRulesFlag) rules.push(lengthChk);

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
          this.commentable = jsonData.commentable;
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
              this.$router.push("/board/notice");
            } else {
              alert("게시글 저장에 실패하였습니다. 관리자에게 문의해주세요.");
              return false;
            }
          });
      }
    },

    async fnLikes() {
      this.likeItem = !this.likeItem;
      await this.axios.get(
        "/rest/board/item/likes/" + this.ancUuid + "/" + this.likeItem
      );
    },

    async fnRegComment() {
      const comment = {
        ancUuid: this.ancUuid,
        ancParentCommentUuid: this.newComment.ancParentCommentUuid,
        ancCommentDepth: this.newComment.depth,
        ancComment: this.newComment.comment,
      };

      this.commentRulesFlag = true;

      let chk = await this.$refs.form.validate();

      if (chk) {
        await this.axios
          .post("/rest/board/item/regComment", JSON.stringify(comment))
          .then((res) => {
            console.log(res);
            if (res.data != "500") {
              alert("댓글 등록이 완료되었습니다.");
              this.$router.go(0);
            } else alert("댓글 등록이 일시적인 오류로 실패하였습니다.");
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

    async fnDeleteItem() {
      if (confirm("게시물을 삭제할까요?")) {
        await this.axios
          .get("/api/board/notice/item/delete/" + this.ancUuid)
          .then((res) => {
            if (res.status == "200") {
              alert("게시물 삭제가 완료되었습니다.");
              this.$router.push("/board/notice");
            } else {
              alert("게시물 삭제에 실패했습니다. 관리자에게 문의해주세요.");
            }
          });
      } else {
        return false;
      }
    },

    fnRegSubReply(cmmt) {
      var nxDpth = cmmt.ancCommentDepth + 1;

      if (nxDpth > 5) {
        alert("더이상 댓글을 달 수 없습니다.");
        return false;
      }
      this.newComment.commentTarget = "@" + cmmt.ancCommenterId.mid;
      this.newComment.ancParentCommentUuid = cmmt.ancCommentUuid;
      this.newComment.depth = nxDpth;
      document.querySelector("#commentEditor").scrollIntoView();
      document.querySelector("textarea#comment").focus();
    },

    async fnDelSubReply(cId) {
      await this.axios.get("/rest/board/item/delComment/" + cId).then((res) => {
        if (res.data == "200") {
          alert("댓글이 삭제되었습니다.");
          this.$router.go(0);
        } else {
          alert("댓글 삭제에 실패했습니다.");
        }
      });
    },

    fnCommentText(comment) {
      var state = comment.ancCommentState;
      var commentTxt = "";
      if (state == 0) {
        commentTxt = comment.ancComment;
      } else {
        commentTxt = "삭제된 댓글입니다.";
      }
      return commentTxt;
    },

    fnCmmtTargetDel() {
      this.newComment.commentTarget = "";
      this.newComment.ancParentCommentUuid = "";
      this.newComment.depth = 0;
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

.mainRegister {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-style: italic;
}

.commentBtnRow {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.mainComment button,
.mainRegister {
  margin-top: -20px;
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
  align-items: baseline;
}

.comment.italic {
  font-style: italic;
  color: darkgray;
}

.register {
  display: flex;
  justify-content: center;
  align-items: baseline;
  font-size: 14px;
  font-style: italic;
}

.subReplyBtnRow {
  display: flex;
  justify-content: flex-start;
}

.regSubReply {
  margin-right: 10px;
}

.regSubReply i,
.replyIcon {
  transform-origin: center;
  transform: rotate(180deg);
}

.regSubReply,
.delSubReply {
  font-size: 0.7em !important;
}

.delSubReply i {
  color: #ff5252;
}

.pad_1 {
  padding-left: 1.8em !important;
}
.pad_2 {
  padding-left: 2.6em !important;
}
.pad_3 {
  padding-left: 3.4em !important;
}
.pad_4 {
  padding-left: 4.2em !important;
}
.pad_5 {
  padding-left: 5em !important;
}
</style>
