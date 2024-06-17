export default {
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
            this.$router.push("/board/notice");
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
};
