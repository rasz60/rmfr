package com.project.rmfr.board.repository;

import com.project.rmfr.entity.ContentHits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentHitsRepository extends JpaRepository<ContentHits, Long>, JpaSpecificationExecutor<ContentHits> {
}
