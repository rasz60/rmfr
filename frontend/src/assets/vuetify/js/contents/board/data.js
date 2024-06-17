export default {
  pageLength: 0,
  page: 1,
  totalCnt: 0,
  items: [],
  searchType: [
    { title: "제목", value: "ancTitle" },
    { title: "작성자", value: "ancRegId" },
    { title: "작성일", value: "ancRegDate" },
    { title: "키워드", value: "ancKw" },
  ],
  sType: "",
  sValue: "",
  sDateValue: null,
  sort: [
    { colName: "anc_title", order: null },
    { colName: "anc_reg_id", order: null },
    { colName: "anc_reg_date", order: null },
    { colName: "anc_hits", order: null },
    { colName: "anc_likes", order: null },
  ],
};
