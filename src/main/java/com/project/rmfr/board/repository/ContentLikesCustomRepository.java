package com.project.rmfr.board.repository;


import com.project.rmfr.board.entity.ContentLikes;
import com.project.rmfr.board.entity.ck.ContentLikesCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ContentLikesCustomRepository {

    public int countByContentId(String contentId);
}
