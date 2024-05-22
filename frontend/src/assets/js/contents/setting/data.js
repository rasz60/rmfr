const data = {
  cert: false,
  details: {
    username: {
      value: "",
      name: "아이디",
      nullable: false,
    },
    password: {
      value: "",
      eValue: "",
      name: "비밀번호",
      nullable: false,
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
      name: "휴대폰",
      nullable: false,
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
};

export default data;
