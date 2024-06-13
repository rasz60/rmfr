package com.project.rmfr.board.repository;


import com.project.rmfr.board.dto.ContentCommentsDto;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
@Repository
public interface ContentCommentsRepository extends JpaRepository<ContentComments, Long>, JpaSpecificationExecutor<ContentComments> {

    Optional<ContentComments> findByAncCommentUuid(String ancCommentUuid);

    Long countByAncUuid(AllNoticeContents anc);

    List<ContentComments> findBySortOrderGreaterThanEqual(int newSortOrder);

}
