export default {
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
    this.selectComment = cmmt.ancComment;
    document
      .querySelector("#scrollPoint")
      .scrollIntoView({ behavior: "smooth" });
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
    this.selectComment = "";
  },

  async fnReplyLike(ancCommentUuid) {
    await this.axios
      .get("/rest/board/item/likeComment/" + ancCommentUuid)
      .then((res) => {
        for (var i in this.ancComments) {
          let target = this.ancComments[i];
          if (target.ancCommentUuid == ancCommentUuid) {
            target.commentLikeFlag = res.data == "201" ? true : false;
            target.likesCount =
              res.data == "201" ? target.likesCount + 1 : target.likesCount - 1;
            break;
          }
        }
      });
  },
};
