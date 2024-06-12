package com.project.rmfr.board.repository.impl;

import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.board.repository.ContentCommentsCustomRepository;
import com.project.rmfr.board.repository.ContentCommentsRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import static com.project.rmfr.board.entity.QContentComments.contentComments;


@Repository
@RequiredArgsConstructor
public class ContentCommentsRepositoryImpl implements ContentCommentsCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ContentCommentsDto> findContentCommentsByAllNoticeContents(ContentComments comments) {
        List<ContentComments> comment = jpaQueryFactory.selectFrom(contentComments)
                .leftJoin(contentComments.ancParentComment)
                .fetchJoin()
                .where(contentComments.ancUuid.eq(comments.getAncUuid()))
                .orderBy(
                        contentComments.sortOrder.asc().nullsFirst()
                ).fetch();
        return convertDtoList(comment);

    }

    public List<ContentCommentsDto> convertDtoList(List<ContentComments> comments) {
        List<ContentCommentsDto> result = new ArrayList<>();
        Map<String, ContentCommentsDto> map = new HashMap<>();
        comments.forEach(c -> {
            ContentCommentsDto dto = ContentCommentsDto.of(c);
            map.put(dto.getAncCommentUuid(), dto);
            if(c.getAncParentComment() != null)
                map.get(c.getAncCommentUuid()).getChildren().add(dto);
            else
                result.add(dto);
        });
        return result;
    }

}
