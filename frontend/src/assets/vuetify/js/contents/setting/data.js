const data = {
  cert: false,
  details: {
    username: {
      value: "",
      name: "아이디",
    },
    password: {
      value: "",
      chkval: "",
      name: "비밀번호",
      chngFlag: false,
      chkd: false,
    },
    email: {
      bValue: "",
      eValue: "",
      name: "이메일",
      nullable: false,
      chkd: false,
    },
    phoneNumber: {
      value: "",
      bHead: "",
      bMid: "",
      bLast: "",
      head: "",
      mid: "",
      last: "",
      name: "휴대폰",
    },
    zipCode: {
      value: "",
      name: "우편번호",
      nullable: true,
    },
    addr1: {
      value: "",
      name: "주소",
      nullable: true,
    },
    addr2: {
      value: "",
      name: "상세주소",
      nullable: true,
    },
  },
  validCode: "",
  cPwChk: false,
};

export default data;
