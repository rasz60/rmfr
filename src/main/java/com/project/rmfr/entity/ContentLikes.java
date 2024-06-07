package com.project.rmfr.entity;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.entity.ck.ContentLikesCK;
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
    public ContentLikes(AllNoticeContents anc, Members likeId) {
        this.contentLikesCK = new ContentLikesCK(anc, likeId);
        this.contentType = "ANC";
    }
}
