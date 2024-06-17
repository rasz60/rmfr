package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.board.repository.*;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.spec.BoardSpecification;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllNoticeContentsServiceImpl implements AllNoticeContentsService {

    private final MemberService memberService;
    private final AllNoticeContentsRepository allNoticeContentsRepository;
    private final ContentHitsRepository contentHitsRepository;
    private final ContentLikesRepository contentLikesRepository;
    private final ContentLikesCustomRepository contentLikesCustomRepository;
    private final ContentCommentsRepository contentCommentsRepository;
    private final ContentCommentsCustomRepository contentCommentsCustomRepository;
    @Override
    public String createItem(Map<String, Object> param) {
        String rst = "";

        try {
            ArrayList<String> ancKw = (ArrayList<String>) param.get("ancKw");
            String keywordStr = "";

            for ( String kw : ancKw ) {
                keywordStr += kw + "|";
            }

            String userId = (String) param.get("userId");

            AllNoticeContents anc = AllNoticeContents.builder()
                                                        .ancTitle((String)param.get("ancTitle"))
                                                        .ancKw(keywordStr)
                                                        .ancContents((String) param.get("ancContents"))
                                                        .member(memberService.loadUser(userId))
                                                        .build();
            rst = allNoticeContentsRepository.save(anc).getAncUuid();

        } catch (Exception e) {
            log.error("createItem() throws exceptions.");
            e.printStackTrace();
        }

        return rst;
    }

    @Override
    @Transactional
    public Page<BoardItemDto> getItems(Map<String, String> param) {
        Page<BoardItemDto> pageItems = null;
        int pg = -1;
        int pglmt = 10;

        try {
            int page = Integer.parseInt(param.get("page"));
            pg += page;
            Page<AllNoticeContents> tmpItems = null;
            if ( param.containsKey("searchType") ) {
                String sType = param.get("searchType");
                String sValue = param.get("searchValue");

                if ( "ancTitle".equals(sType) ) {
                    tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2).and(BoardSpecification.withAncTitle(sValue)), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));
                }
                else if ( "ancRegId".equals(sType) ) {
                    tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2).and(BoardSpecification.withAncRegId(sValue)), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));
                }
                else if ( "ancRegDate".equals(sType) ) {
                    String[] sValueArr = sValue.split("\\|");
                    LocalDateTime sDate = strToDate(sValueArr[0], 0);
                    LocalDateTime eDate = strToDate(sValueArr[1], 1);

                    tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2).and(BoardSpecification.withAncRegDate(sDate, eDate)), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));
                }
                else if ( "ancKw".equals(sType) ) {
                    tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2).and(BoardSpecification.withAncKw(sValue)), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));
                }
            } else {
                tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));
            }
            pageItems = tmpItems.map(tmpItem -> new BoardItemDto(tmpItem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageItems;
    }

    @Override
    public Page<BoardItemDto> findItems(String page, String searchType, String searchKeywords) {
        Page<BoardItemDto> pageItems = null;
        try {
            int pg = Integer.parseInt(page) -1;
            int pglmt = 10;

            Page<AllNoticeContents> tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2)
                                                                                 , PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));

            pageItems = tmpItems.map(tmpItem -> new BoardItemDto(tmpItem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageItems;
    }


    @Override
    @Transactional
    public BoardItemDto getItemDetails(String itemId, String mId) {
        BoardItemDto dto = new BoardItemDto();
        try {
            Optional<AllNoticeContents> ancOptional = allNoticeContentsRepository.findByAncUuid(itemId);

            if ( ancOptional.isPresent() ) {
                AllNoticeContents anc = ancOptional.get();
                dto = BoardItemDto.of(anc);

                if (anc.getAncAuth() > 0 ) {
                    dto.setVisible(false);
                }

                if (! "guest".equals(mId) ) {
                    Members loginUser = memberService.loadUser(mId);
                    dto.setCommentable(true);
                    List<ContentCommentsDto> ancComments = dto.getAncComments();

                    for ( ContentCommentsDto commentDto : ancComments ) {
                        // 댓글 삭제 가능 여부
                        boolean editable = mId.equals(commentDto.getAncCommenterId().getMId());
                        commentDto.setCommentEditable(editable);

                        // 로그인 유저의 좋아요 클릭 여부
                        ContentLikesCK ck = new ContentLikesCK(commentDto.getAncCommentUuid(), loginUser);
                        Long cnt = contentLikesRepository.countByContentLikesCKAndContentType(ck, "COM");
                        commentDto.setCommentLikeFlag(cnt > 0);

                        // 댓글의 좋아요 수
                        int cnt2 = contentLikesCustomRepository.countByContentId(commentDto.getAncCommentUuid());
                        commentDto.setLikesCount(cnt2);
                    }

                    dto.setAncComments(ancComments);

                    if ( loginUser.getMId().equals(dto.getAncRegId()) || loginUser.getMLevel() > 1 ) {
                        dto.setEditable(true);
                        dto.setDeletable(true);
                        dto.setVisible(true);
                    }

                    if ( !mId.equals(dto.getAncRegId())) {
                        hitsUp(anc, loginUser);
                    }

                    dto.setLikeItem(setLikeItem(anc, loginUser));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto;
    };

    @Override
    public String updateItem(Map<String, Object> param) {
        String rst = "";

        try {
            String ancUuid = (String) param.get("ancUuid");
            Optional<AllNoticeContents> optAnc = allNoticeContentsRepository.findByAncUuid(ancUuid);

            if (optAnc.isPresent()) {
                AllNoticeContents anc = optAnc.get();

                String ancTitle = (String) param.get("ancTitle");
                String ancContents = (String) param.get("ancContents");


                ArrayList<String> ancKw = (ArrayList<String>) param.get("ancKw");
                String keywordStr = "";

                for (String kw : ancKw) {
                    keywordStr += kw + "|";
                }

                String userId = (String) param.get("userId");

                anc.setAncTitle(ancTitle);
                anc.setAncContents(ancContents);
                anc.setAncKw(keywordStr);
                anc.setAncUpdaterId(memberService.loadUser(userId));
                anc.setAncUpdateDate(LocalDateTime.now());

                rst = allNoticeContentsRepository.save(anc).getAncUuid();
                rst = ancUuid.equals(rst) ? "200" : "500";

            }
        } catch (Exception e) {
            log.error("updateItem() throws exceptions.");
            e.printStackTrace();
        }

        return rst;
    }

    @Override
    public String deleteItem(String ancUuid, String userId) {
        String rst = "";

        try {
            Optional<AllNoticeContents> optAnc = allNoticeContentsRepository.findByAncUuid(ancUuid);

            if (optAnc.isPresent()) {
                AllNoticeContents anc = optAnc.get();

                anc.setAncState(4);
                anc.setAncUpdaterId(memberService.loadUser(userId));
                anc.setAncUpdateDate(LocalDateTime.now());

                rst = allNoticeContentsRepository.save(anc).getAncUuid();
                rst = ancUuid.equals(rst) ? "200" : "500";

            }
        } catch (Exception e) {
            log.error("deleteItem() throws exceptions.");
            e.printStackTrace();
        }

        return rst;
    }

    @Override
    public String chngLikeFlag(String ancUuid, boolean flag, String mId) {
        String rst = "";
        try {
            ContentLikes like = ContentLikes.builder()
                    .contentId(ancUuid)
                    .likeId(memberService.loadUser(mId))
                    .contentType("ANC")
                    .build();
            if (flag) {
                rst = "ANC".equals(contentLikesRepository.save(like).getContentType()) ? "200" : "500";
            } else {
                contentLikesRepository.delete(like);
                rst = "200";
            }
        } catch (Exception e) {
            e.printStackTrace();
            rst = "500";
        }

        return rst;
    }

    @Override
    @Transactional
    public String regComment(Map<String, Object> param, String mId) {
        String rst = "";

        try {
            String ancUuid = (String) param.get("ancUuid");
            Optional<AllNoticeContents> optionAnc = allNoticeContentsRepository.findByAncUuid(ancUuid);

            if (optionAnc.isPresent()) {
                AllNoticeContents anc = optionAnc.get();
                String ancParentCommentUuid = (String) param.get("ancParentCommentUuid");
                String ancComment = (String) param.get("ancComment");
                int ancDepth = (int) param.get("ancCommentDepth");

                //댓글일 때 (ancDepth = "")
                if ("".equals(ancParentCommentUuid)) {
                    // 전체 댓글의 마지막 순서로 댓글 입력
                    ContentComments comment = new ContentComments(
                            ancComment
                            , contentCommentsRepository.countByAncUuid(anc).intValue()
                            , anc
                            , memberService.loadUser(mId)
                    );
                    rst = contentCommentsRepository.save(comment).getAncUuid().getAncUuid();
                }
                //대댓글일 때 (ancDepth > 1)
                else {
                    // 부모 댓글 객체 조회
                    Optional<ContentComments> pComment = contentCommentsRepository.findByAncCommentUuid(ancParentCommentUuid);

                    // 부모 댓글이 존재할 때
                    if (pComment.isPresent()) {
                        // 부모 댓글
                        ContentComments parentComment = pComment.get();

                        // 부모 댓글의 모든 자식 댓글 조회
                        List<ContentComments> childList = contentCommentsCustomRepository.findContentCommentsByAllNoticeContents(pComment.get());

                        // 자식 댓글 중 sortOrder가 가장 높은 값 조회
                        int maxSortOrder = getMaxSortOrder(childList, parentComment.getSortOrder());
                        int newSortOrder = maxSortOrder + 1; // 자식 댓글 중 가장 마지막 순서의 다음으로 설정

                        // 새로 작성된 댓글이 들어갈 차례 이 후의 기존 댓글 전체 조회
                        List<ContentComments> lastComments = contentCommentsRepository.findBySortOrderGreaterThanEqual(newSortOrder);

                        // 기존 댓글의 순서를 모두 + 1
                        for (ContentComments cmm : lastComments) {
                            cmm.setSortOrder(cmm.getSortOrder() + 1);
                            contentCommentsRepository.save(cmm);
                        }

                        // newSortOrder를 가진 comment 신규 insert
                        ContentComments comment = new ContentComments(
                                parentComment
                                , ancComment
                                , ancDepth
                                , newSortOrder
                                , anc
                                , memberService.loadUser(mId)
                        );
                        rst = contentCommentsRepository.save(comment).getAncUuid().getAncUuid();
                    }
                }
            }
        } catch (Exception e) {
            log.error("regComment throw exceptions.");
            e.printStackTrace();
        } finally {
            // 댓글 작성이 실패한 경우
            if ( "".equals(rst) ) {
                rst = "500";
            }
        }

        return rst;
    }

    @Override
    public String delComment(String ancCommentUuid) {
        String rst = "";
        int commentStatus = 0;
        if (! "".equals(ancCommentUuid) ) {
            Optional<ContentComments> commentOption = contentCommentsRepository.findByAncCommentUuid(ancCommentUuid);

            if ( commentOption.isPresent() ) {
                ContentComments comment = commentOption.get();
                comment.setAncCommentState(1);
                commentStatus = contentCommentsRepository.save(comment).getAncCommentState();
            }
        }
        rst = commentStatus == 1 ? "200" : "500";

        return rst;
    }

    public String likeComment(String ancCommentUuid, Principal principal) {
        String rst = "";

        try {
            String mId = "";
            if ( principal != null ) mId = principal.getName();
            ContentLikes like = ContentLikes.builder().contentId(ancCommentUuid).likeId(memberService.loadUser(mId)).contentType("COM").build();

            long chk = contentLikesRepository.countByContentLikesCKAndContentType(like.getContentLikesCK(), like.getContentType());

            if ( chk > 0 ) {
                contentLikesRepository.delete(like);
                rst = "202";
            } else {
                rst = contentLikesRepository.save(like).getContentType();
                rst = "COM".equals(rst) ? "201" : "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("likeComment() throw exceptions.");
        }

        return rst;
    }


    public String hitsUp(AllNoticeContents anc, Members loginUser) {
        String rst = "";
        boolean chk = true;
        try {

            List<ContentHits> ch = contentHitsRepository.findAll(BoardSpecification.withAncHitsId(loginUser.getMEntrId())
                                                                                    .and(BoardSpecification.withAncUuid(anc.getAncUuid())));


            if ( ch.size() == 0 ) {
                ContentHits hit = ContentHits.builder()
                                            .anc(anc)
                                            .hits(loginUser)
                                            .build();
                rst = !"".equals(contentHitsRepository.save(hit).getContentHitsCK().getAncUuid().getAncUuid()) ? "200" : "500";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return rst;
    }

    public boolean setLikeItem(AllNoticeContents anc, Members loginUser) {
        boolean chk = false;

        try {
            List<ContentLikes> ch = contentLikesRepository.findAll(BoardSpecification.withContentLikerId(loginUser.getMEntrId())
                    .and(BoardSpecification.withContentId(anc.getAncUuid())));

            chk = ch.size() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return chk;
    }

    public int getMaxSortOrder(List<ContentComments> commentsList, int pSortOrder) {
        // 부모 댓글의 sortOrder에서부터 시작
        int maxSortOrder = pSortOrder;

        // commentsList에 있는 댓글 중 sortOrder가 가장 큰 값을 찾음
        for( ContentComments c : commentsList )
            maxSortOrder = Math.max(c.getSortOrder(), maxSortOrder);

        return maxSortOrder;
    }

    public LocalDateTime strToDate(String dateStr, int type) {
        int YYYY = 0;
        int MM = 0;
        int DD = 0;
        int HH = 0;
        int mm = 0;
        int SS = 0;
        int length = dateStr.length();
        if ( length > 8 ) {
            YYYY = Integer.parseInt(dateStr.substring(0, 4));
            MM = Integer.parseInt(dateStr.substring(4, 6))-1;
            DD = Integer.parseInt(dateStr.substring(6, 8));
            HH = Integer.parseInt(dateStr.substring(8, 10));
            mm = Integer.parseInt(dateStr.substring(10, 12));
            SS = Integer.parseInt(dateStr.substring(12, 14));
        } else {
            YYYY = Integer.parseInt(dateStr.substring(0, 4));
            MM = Integer.parseInt(dateStr.substring(4, 6));
            DD = Integer.parseInt(dateStr.substring(6));
            HH = type == 0 ? 0 : 23;
            mm = type == 0 ? 0 : 59;
            SS = type == 0 ? 0 : 59;
        }
        return LocalDateTime.of(YYYY, MM, DD, HH, mm, SS);
    }
}
