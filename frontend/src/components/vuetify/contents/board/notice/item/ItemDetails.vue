<script setup>
import boardItemDetailsData from "@v-js/contents/board/item/details/data.js";
import boardItemDetailsMethods from "@v-js/contents/board/item/details/methods.js";
</script>

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
      <v-row class="body-row" id="scrollPoint">
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
          id="selectComment"
          class="body-row"
          v-show="selectComment != ''"
          ref="scrollBox"
        >
          <v-col cols="2" class="mainRegister">
            <v-chip @click="fnCmmtTargetDel" color="teal">
              {{ newComment.commentTarget }}님에게
            </v-chip>
          </v-col>
          <v-col cols="9">
            <v-textarea
              rows="1"
              auto-grow
              readonly
              v-model="selectComment"
            ></v-textarea>
          </v-col>
          <v-divider></v-divider>
        </v-row>
        <v-row
          id="commentEditor"
          class="body-row"
          :v-show="!editmode && commentable"
        >
          <v-col cols="1">
            <v-icon
              icon="fas fa-reply"
              class="replyIcon"
              size="20px"
              v-show="selectComment != ''"
            ></v-icon>
          </v-col>
          <v-col cols="10">
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
          <v-col cols="1" class="mainCommentBtn">
            <v-btn
              icon="far fa-comment-dots"
              @click="fnRegComment"
              v-show="commentable"
            ></v-btn>
          </v-col>
        </v-row>
        <v-divider></v-divider>
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
            cols="8"
            :class="
              'comment pad_' +
              comment.ancCommentDepth +
              (comment.ancCommentState != 0 ? ' italic' : '')
            "
          >
            <p class="commentText">
              <v-icon
                icon="fas fa-reply"
                class="replyIcon"
                size="12px"
                v-show="comment.ancCommentDepth > 0"
              ></v-icon
              >&nbsp;
              {{ fnCommentText(comment) }}
            </p>
            <span
              class="regSubReply"
              v-show="comment.ancCommentState == 0 && commentable"
              @click="fnRegSubReply(comment)"
            >
              답글쓰기
            </span>
            &nbsp;&nbsp;
            <span
              class="delSubReply"
              v-show="comment.ancCommentState == 0 && comment.commentEditable"
            >
              댓글삭제
            </span>
          </v-col>
          <v-col cols="2" class="commentBtnRow">
            <v-icon
              density="comfortable"
              :icon="comment.commentLikeFlag ? 'fas fa-heart' : 'far fa-heart'"
              :color="comment.commentLikeFlag ? 'red' : ''"
              v-show="comment.ancCommentState == 0 && commentable"
              @click="fnReplyLike(comment.ancCommentUuid, $event)"
            ></v-icon>
            &nbsp;
            <v-icon
              size="1em"
              v-show="comment.ancCommentState == 0 && commentable"
              :icon="'fas fa-' + comment.likesCount"
            ></v-icon>
          </v-col>
          <v-divider></v-divider>
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
    return boardItemDetailsData;
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
  methods: boardItemDetailsMethods,
};
</script>

<style>
@import "@v-css/contents/board/notice/item/itemDetails.css";
</style>
