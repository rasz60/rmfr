package com.project.rmfr.board.spec;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ContentLikes;
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
                builder.equal(root.get("contentLikesCK").get("contentId").get("ancUuid"), ancUuid)
        );
    }

    public static Specification<ContentLikes> withContentLikerId(String mEntrId) {
        return (Specification<ContentLikes>) ((root, query, builder) ->
                builder.equal(root.get("contentLikesCK").get("contentLikerId").get("mEntrId"), mEntrId)
        );
    }

}
