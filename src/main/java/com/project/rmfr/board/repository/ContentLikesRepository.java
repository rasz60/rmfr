package com.project.rmfr.board.repository;


import com.project.rmfr.entity.ContentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentLikesRepository extends JpaRepository<ContentLikes, Long>, JpaSpecificationExecutor<ContentLikes> {
}
