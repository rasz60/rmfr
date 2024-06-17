export default {
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
};
