package com.project.rmfr.board.repository.impl;

import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.repository.ContentLikesCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.project.rmfr.board.entity.QContentLikes.contentLikes;


@Repository
@RequiredArgsConstructor
public class ContentLikesRepositoryImpl implements ContentLikesCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public int countByContentId(String contentId) {
        List<ContentLikes> likes =
                jpaQueryFactory
                        .selectFrom(contentLikes)
                        .where(contentLikes.contentLikesCK.contentId.eq(contentId))
                        .fetch();
        return likes.size();
    }
}
