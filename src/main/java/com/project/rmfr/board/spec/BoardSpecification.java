package com.project.rmfr.board.spec;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ContentLikes;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    public static Specification<AllNoticeContents> withAncTitle(String ancTitle) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.like(root.get("ancTitle"), "%"+ancTitle+"%")
        );
    }
    public static Specification<AllNoticeContents> withAncRegId(String ancRegId) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.like(root.get("ancRegId").get("mId"), "%"+ancRegId+"%")
        );
    }
    public static Specification<AllNoticeContents> withAncRegDate(LocalDateTime sDate, LocalDateTime eDate) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.between(root.get("ancRegDate"), sDate, eDate)
        );
    }
    public static Specification<AllNoticeContents> withAncKw(String ancKw) {
        return (Specification<AllNoticeContents>) ((root, query, builder) ->
                builder.like(root.get("ancKw"), "%"+ancKw+"%")
        );
    }
}
