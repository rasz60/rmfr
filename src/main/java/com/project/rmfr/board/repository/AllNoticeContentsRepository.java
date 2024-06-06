package com.project.rmfr.board.repository;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllNoticeContentsRepository  extends JpaRepository<AllNoticeContents, Long> {

    Optional<AllNoticeContents> findByAncUuid(String ancUuid);
}
