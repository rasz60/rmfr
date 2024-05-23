import NoticeBoardList from "@/components/contents/board/notice/board/BoardList.vue";
import CreateNoticeItem from "@/components/contents/board/notice/item/ItemCreate.vue";

export default [
  {
    path: "notice",
    component: NoticeBoardList,
  },
  {
    path: "notice/item/c",
    component: CreateNoticeItem,
  },
];
