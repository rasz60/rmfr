package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ck.ContentHitsCK;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.board.repository.*;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.service.ContentCommentsService;
import com.project.rmfr.board.service.ContentHitsService;
import com.project.rmfr.board.service.ContentLikesService;
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

    private final ContentLikesService contentLikesService;
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
            Specification<AllNoticeContents> spec = setSpecification(param);
            List<Sort.Order> sortOrder = setSortOrder(param);

            tmpItems = allNoticeContentsRepository.findAll(spec, PageRequest.of(pg, pglmt, Sort.by(sortOrder)));

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
                        int cnt = contentLikesService.countByContentLikesCKAndContentType(commentDto.getAncCommentUuid(), loginUser, "COM");
                        commentDto.setCommentLikeFlag(cnt > 0);

                        // 댓글의 좋아요 수
                        int cnt2 = contentLikesService.countByContentId(commentDto.getAncCommentUuid());
                        commentDto.setLikesCount(cnt2);

                        if ( commentDto.getAncCommentDepth() == 0 ) {
                            commentDto.setDisplayFlag(true);
                        }
                    }

                    dto.setAncComments(ancComments);

                    if ( loginUser.getMId().equals(dto.getAncRegId()) || loginUser.getMLevel() > 1 ) {
                        dto.setEditable(true);
                        dto.setDeletable(true);
                        dto.setVisible(true);
                    }

                    int likeCnt = contentLikesService.countByContentLikesCKAndContentType(itemId, loginUser, "ANC");
                    dto.setLikeItem(likeCnt > 0);
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
    public AllNoticeContents findByAncUuid(String ancUuid) {
        AllNoticeContents anc = null;

        try {
            Optional<AllNoticeContents> optAnc = allNoticeContentsRepository.findByAncUuid(ancUuid);

            if ( optAnc.isPresent() ) {
                anc = optAnc.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return anc;
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

    public Specification<AllNoticeContents> setSpecification(Map<String, String> param) {
        Specification<AllNoticeContents> spec = BoardSpecification.withAncState(2);
        String sType = "";
        String sValue = "";
        try {

            if ( param.containsKey("sType") ) {
                sType = param.get("sType");
                sValue = param.get("sValue");

                if ( "ancTitle".equals(sType) ) {
                    spec = spec.and(BoardSpecification.withAncTitle(sValue));
                }
                else if ( "ancRegId".equals(sType) ) {
                    spec = spec.and(BoardSpecification.withAncRegId(sValue));
                }
                else if ( "ancRegDate".equals(sType) ) {
                    String[] sValueArr = sValue.split("\\|");
                    LocalDateTime sDate = strToDate(sValueArr[0], 0);
                    LocalDateTime eDate = strToDate(sValueArr[1], 1);

                    spec = spec.and(BoardSpecification.withAncRegDate(sDate, eDate));
                }
                else if ( "ancKw".equals(sType) ) {
                    spec = spec.and(BoardSpecification.withAncKw(sValue));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return spec;
    }

    public List<Sort.Order> setSortOrder(Map<String, String> param) {
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        String sortOrder = "";
        try {
            if ( param.containsKey("sortOrder") ) {
                sortOrder = param.get("sortOrder");
                String[] ordersArr = sortOrder.split(",");

                for ( String order : ordersArr ) {
                    String[] orderArr = order.split(" ");
                    String colName = orderArr[0];
                    String direction = orderArr[1];

                    if ("ASC".equals(direction))
                        orderList.add(new Sort.Order(Sort.Direction.ASC, colName));
                    else
                        orderList.add(new Sort.Order(Sort.Direction.DESC, colName));
                }
            } else {
                orderList.add(new Sort.Order(Sort.Direction.DESC, "ancRegDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderList;
    }
}
