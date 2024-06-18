package com.project.rmfr.board.service.impl;

import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.repository.ContentCommentsCustomRepository;
import com.project.rmfr.board.repository.ContentCommentsRepository;
import com.project.rmfr.board.service.AllNoticeContentsService;
import com.project.rmfr.board.service.ContentCommentsService;
import com.project.rmfr.board.service.ContentLikesService;
import com.project.rmfr.board.spec.BoardSpecification;
import com.project.rmfr.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.AbstractDocument;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public int getMaxSortOrder(List<ContentComments> commentsList, int pSortOrder) {
        // 부모 댓글의 sortOrder에서부터 시작
        int maxSortOrder = pSortOrder;

        // commentsList에 있는 댓글 중 sortOrder가 가장 큰 값을 찾음
        for( ContentComments c : commentsList )
            maxSortOrder = Math.max(c.getSortOrder(), maxSortOrder);

        return maxSortOrder;
    }
}
