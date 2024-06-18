package com.project.rmfr.board.repository;


import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentLikesRepository extends JpaRepository<ContentLikes, Long>, JpaSpecificationExecutor<ContentLikes> {

    Long countByContentLikesCKAndContentType(ContentLikesCK contentLikesCK, String contentType);

}
