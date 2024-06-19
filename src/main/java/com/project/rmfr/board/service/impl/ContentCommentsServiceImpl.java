package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.board.repository.ContentCommentsCustomRepository;
import com.project.rmfr.board.repository.ContentCommentsRepository;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.service.ContentCommentsService;
import com.project.rmfr.board.service.ContentLikesService;
import com.project.rmfr.board.spec.BoardSpecification;
import com.project.rmfr.member.entity.Members;
import com.project.rmfr.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentCommentsServiceImpl implements ContentCommentsService {
    private final MemberService memberService;
    private final ContentCommentsRepository contentCommentsRepository;
    private final ContentCommentsCustomRepository contentCommentsCustomRepository;
    private final AllNoticeContentsService allNoticeContentsService;
    private final ContentLikesService contentLikesService;

    @Override
    @Transactional
    public String regComment(Map<String, Object> param, String mId) {
        String rst = "";

        try {
            String ancUuid = (String) param.get("ancUuid");
            AllNoticeContents anc = allNoticeContentsService.findByAncUuid(ancUuid);

            if (anc != null) {
                String ancParentCommentUuid = (String) param.get("ancParentCommentUuid");
                String ancComment = (String) param.get("ancComment");
                int ancDepth = (int) param.get("ancCommentDepth");

                //댓글일 때 (ancDepth = "")
                if ("".equals(ancParentCommentUuid)) {
                    // 전체 댓글의 마지막 순서로 댓글 입력
                    ContentComments comment = new ContentComments(
                            ancComment
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

                        // newSortOrder를 가진 comment 신규 insert
                        ContentComments comment = new ContentComments(
                                parentComment
                                , ancComment
                                , ancDepth
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
    @Override
    public String likeComment(String ancCommentUuid, Principal principal) {
        String rst = "";

        try {
            String mId = "";
            if ( principal != null ) mId = principal.getName();
            ContentLikes like = ContentLikes.builder().contentId(ancCommentUuid).likeId(memberService.loadUser(mId)).contentType("COM").build();

            int chk = contentLikesService.countByContentLikesCKAndContentType(like.getContentLikesCK(), like.getContentType());

            if ( chk > 0 ) {
                contentLikesService.deleteLikes(like);
                rst = "202";
            } else {
                rst = contentLikesService.saveLikes(like);
                rst = "COM".equals(rst) ? "201" : "500";
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("likeComment() throw exceptions.");
        }

        return rst;
    }
    @Override
    public List<ContentCommentsDto> getComments(String ancParentCommentUuid, String mId) {
        List<ContentCommentsDto> dtos = new ArrayList<>();
        try {
            Optional<ContentComments> pComment= contentCommentsRepository.findByAncCommentUuid(ancParentCommentUuid);

            if ( pComment.isPresent() ) {
                List<ContentComments> comments = contentCommentsRepository.findByAncParentComment(pComment.get());

                if ( !comments.isEmpty() ) {
                    dtos = ContentCommentsDto.of(comments);

                    for ( ContentCommentsDto dto : dtos ) {
                        if (!"guest".equals(mId)) {
                            Members loginUser = memberService.loadUser(mId);
                            // 댓글 삭제 가능 여부
                            boolean editable = mId.equals(dto.getAncCommenterId().getMId());
                            dto.setCommentEditable(editable);

                            // 로그인 유저의 좋아요 클릭 여부
                            ContentLikesCK ck = new ContentLikesCK(dto.getAncCommentUuid(), loginUser);
                            int cnt = contentLikesService.countByContentLikesCKAndContentType(dto.getAncCommentUuid(), loginUser, "COM");
                            dto.setCommentLikeFlag(cnt > 0);
                        }

                        // 댓글의 좋아요 수
                        int cnt2 = contentLikesService.countByContentId(dto.getAncCommentUuid());
                        dto.setLikesCount(cnt2);
                    }
                    Collections.sort(dtos);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dtos;
    }
}
