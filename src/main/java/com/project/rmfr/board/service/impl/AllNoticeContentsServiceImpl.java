package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.dto.BoardItemDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.repository.AllNoticeContentsRepository;
import com.project.rmfr.board.repository.ContentCommentsRepository;
import com.project.rmfr.board.repository.ContentHitsRepository;
import com.project.rmfr.board.repository.ContentLikesRepository;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllNoticeContentsServiceImpl implements AllNoticeContentsService {

    private final MemberService memberService;
    private final AllNoticeContentsRepository allNoticeContentsRepository;
    private final ContentHitsRepository contentHitsRepository;
    private final ContentLikesRepository contentLikesRepository;
    private final ContentCommentsRepository contentCommentsRepository;
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


    @Transactional
    public Page<BoardItemDto> getItems(String page) {
        Page<BoardItemDto> pageItems = null;
        try {
            int pg = Integer.parseInt(page) -1;
            int pglmt = 10;

            Page<AllNoticeContents> tmpItems = allNoticeContentsRepository.findAll(BoardSpecification.withAncState(2), PageRequest.of(pg, pglmt, Sort.by(Sort.Direction.DESC, "ancRegDate")));

            pageItems = tmpItems.map(tmpItem -> new BoardItemDto(tmpItem));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageItems;
    }

    @Transactional
    public BoardItemDto getItemDetails(String itemId, String mId) {
        BoardItemDto dto = new BoardItemDto();
        try {
            Optional<AllNoticeContents> ancOptional = allNoticeContentsRepository.findByAncUuid(itemId);

            if ( ancOptional.isPresent() ) {
                AllNoticeContents anc = ancOptional.get();
                dto = new BoardItemDto(ancOptional.get());

                if (anc.getAncAuth() > 0 ) {
                    dto.setVisible(false);
                }

                if (! "guest".equals(mId) ) {
                    Members loginUser = memberService.loadUser(mId);

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
                    .anc(allNoticeContentsRepository.findByAncUuid(ancUuid).get())
                    .likeId(memberService.loadUser(mId))
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
    public String regComment(Map<String, Object> param, String mId) {
        String rst = "";
        String ancUuid = (String) param.get("ancUuid");
        String ancParentCommentUuid = (String) param.get("ancParentCommentUuid");
        String ancComment = (String) param.get("ancComment");
        String ancDepth = (String) param.get("ancDepth");

        if ( "".equals(ancParentCommentUuid) ) {
            ContentComments comment = ContentComments.builder()
                    .ancParentCommentUuid(ancParentCommentUuid)
                    .comment(ancComment)
                    .anc(allNoticeContentsRepository.findByAncUuid(ancUuid).get())
                    .member(memberService.loadUser(mId))
                    .build();
            contentCommentsRepository.save(comment);
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
}