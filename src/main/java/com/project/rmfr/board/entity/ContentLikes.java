package com.project.rmfr.board.entity;

import com.project.rmfr.board.entity.ck.ContentLikesCK;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="contentLikes")
@Getter
@Setter
public class ContentLikes {

    @EmbeddedId
    private ContentLikesCK contentLikesCK;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String contentType;

    public ContentLikes() {}

    @Builder
    public ContentLikes(String contentId, Members likeId, String contentType) {
        this.contentLikesCK = new ContentLikesCK(contentId, likeId);
        this.contentType = contentType;
    }
}
