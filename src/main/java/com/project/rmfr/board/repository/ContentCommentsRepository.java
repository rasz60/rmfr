package com.project.rmfr.board.repository;


import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ContentCommentsRepository extends JpaRepository<ContentComments, Long>, JpaSpecificationExecutor<ContentComments> {

    Optional<ContentComments> findByAncCommentUuid(String ancCommentUuid);
}
