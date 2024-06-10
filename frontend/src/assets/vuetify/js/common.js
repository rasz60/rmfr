export default {
install(app, options) {
    const loginChk = {
        name: "loginChk",
        async fn_loginChk() {
            // 로그인 실패 시, loginError=true로 return
            var url = location.href;
            var query = url.substring(url.indexOf("?"));
            var param = new URLSearchParams(query);

            if (param.size > 0 && param.has("loginError")) {
                alert("로그인 정보를 다시 확인해주세요.");
            } else {
                await this.axios.get("/rest/member/loginchk").then((res) => {
                var jsonData = res.data.info;
                if (jsonData != "" && jsonData != null) {
                    // 결과
                    var mId = jsonData.mid;
                    var thum = jsonData.thum;
                    var mPwUpdateDate = jsonData.mpwUpdateDate;
                    var mLevel = jsonData.mlevel;

                    // data setting
                    this.info.mId = mId;
                    this.info.mLevel = mLevel;
                    this.info.mPwUpdateDate = mPwUpdateDate;

                    if (thum == "" || thum == null) {
                    this.info.tmpImg = this.info.mId.substring(0, 1);

                    const rColor = Math.floor(Math.random() * 128 + 64);
                    const gColor = Math.floor(Math.random() * 128 + 64);
                    const bColor = Math.floor(Math.random() * 128 + 64);

                    document.querySelector("a#tmpImg").style.backgroundColor =
                        "rgb(" + rColor + "," + gColor + "," + bColor + ")";
                    } else {
                    this.info.mThum = thum;
                    }
                    var updateDate = new Date(mPwUpdateDate);
                    updateDate.setDate(updateDate.getDate() + 89);
                    var diff = Math.abs(updateDate - new Date());
                    var diffDate = Math.ceil(diff / (1000 * 60 * 60 * 24));

                    this.info.mUd = diffDate;

                    this.login = true;
                }
                });
            }
        }
    };
    app.config.globalProperties.$loginUser = loginChk;
}