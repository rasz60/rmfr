import NoticeBoardList from "@v-components/contents/board/notice/board/BoardList.vue";
import CreateNoticeItem from "@v-components/contents/board/notice/item/ItemCreate.vue";
import DetailsNoticeItem from "@v-components/contents/board/notice/item/ItemDetails.vue";

export default [
  {
    path: "notice",
    component: NoticeBoardList,
  },
  {
    path: "notice/item/c",
    component: CreateNoticeItem,
  },
  {
    path: "notice/item/d",
    component: DetailsNoticeItem,
  },
];
