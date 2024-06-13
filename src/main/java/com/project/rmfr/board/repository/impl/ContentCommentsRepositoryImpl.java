package com.project.rmfr.board.repository.impl;

import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.repository.ContentCommentsCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.*;

import org.springframework.stereotype.Repository;

import static com.project.rmfr.board.entity.QContentComments.contentComments;


@Repository
@RequiredArgsConstructor
public class ContentCommentsRepositoryImpl implements ContentCommentsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<ContentComments> findContentCommentsByAllNoticeContents(ContentComments comment) {
        // 부모 댓글보다 depth가 높은 전체 댓글을 depth 순으로 조회
        List<ContentComments> commentsList =
                jpaQueryFactory
                    .selectFrom(contentComments)
                    .where(contentComments.ancUuid.eq(comment.getAncUuid()).and(contentComments.ancCommentDepth.gt(comment.getAncCommentDepth())))
                    .orderBy(
                            contentComments.ancCommentDepth.asc().nullsFirst()
                    )
                        .fetch();

        return getChildList(commentsList, comment);
    }

    public List<ContentComments> getChildList(List<ContentComments> commentList, ContentComments comment) {
        List<ContentComments> result = new ArrayList<>();

        // 사용자가 선택한 부모 댓글의 모든 자식 댓글 uuid를 담을 list
        List<String> parentList = new ArrayList<>();

        // 사용자가 선택한 부모 댓글의 uuid를 먼저 add
        parentList.add(comment.getAncCommentUuid());

        // findContentCommentsByAllNoticeContents() 결과 리스트 전체 반복
        commentList.forEach(c -> {
            // 반복문으로 한 건씩 조회하여 해당 댓글 객체의 부모 댓글 uuid
            String cpId = c.getAncParentComment().getAncCommentUuid();
            boolean match = false;
            // 부모 댓글 uuid 리스트 전체 반복
            for ( String pId : parentList ) {
                match = cpId.equals(pId);
                // 리스트에 현재 객체의 부모 uuid가 있는 경우 result에 add
                if ( match ) {
                    result.add(c);
                    break;
                }
            }

            // 다음 depth의 댓글 중 자식 댓글을 찾을 수 있도록 해당 uuid를 부모 객체 리스트에 추가
            if ( match ) parentList.add(c.getAncCommentUuid());
        });

        return result;
    }
}
