package com.project.rmfr.board.repository;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentHits;
import com.project.rmfr.board.entity.ck.ContentHitsCK;
import com.project.rmfr.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentHitsRepository extends JpaRepository<ContentHits, Long>, JpaSpecificationExecutor<ContentHits> {
    Long countByContentHitsCK(ContentHitsCK ck);
}
