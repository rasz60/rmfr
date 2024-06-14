package com.project.rmfr.board.spec;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ContentLikes;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BoardSpecification {

    public static Specification<AllNoticeContents> withAncState(int ancState) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.equal(root.get("ancState"), ancState)
        );
    }

    public static Specification<ContentHits> withAncUuid(String ancUuid) {
        return (Specification<ContentHits>) ((root, query, builder) ->
                builder.equal(root.get("contentHitsCK").get("ancUuid").get("ancUuid"), ancUuid)
        );
    }

    public static Specification<ContentHits> withAncHitsId(String mEntrId) {
        return (Specification<ContentHits>) ((root, query, builder) ->
                builder.equal(root.get("contentHitsCK").get("ancHitsId").get("mEntrId"), mEntrId)
        );
    }


    public static Specification<ContentLikes> withContentId(String ancUuid) {
        return (Specification<ContentLikes>) ((root, query, builder) ->
                builder.equal(root.get("contentLikesCK").get("contentId"), ancUuid)
        );
    }

    public static Specification<ContentLikes> withContentLikerId(String mEntrId) {
        return (Specification<ContentLikes>) ((root, query, builder) ->
                builder.equal(root.get("contentLikesCK").get("contentLikerId").get("mEntrId"), mEntrId)
        );
    }

    public static Specification<ContentComments> findByParent(ContentComments parent) {
        return (Specification<ContentComments>) ((root, query, builder) -> {
            if (parent == null) {
                return builder.isNull(root.get("ancCommentUuid"));
            } else {
                return builder.equal(root.get("ancParentComment"), parent);
            }
        });
    }

    public static Specification<ContentComments> findRecursiveByParent(ContentComments parent) {
        // parentUuid 하위 모든 댓글 중 sortOrder Max
        return (Specification<ContentComments>) ((root, query, builder) -> {
            Predicate predicate = findByParent(parent).toPredicate(root, query, builder);

            int count = 0;

            // 재귀적으로 하위 부서를 검색합니다.
            for (ContentComments child : parent.getChildren()) {
                predicate = builder.or(findRecursiveByParent(child).toPredicate(root, query, builder));
            }
            return predicate;
        });
    }

}
