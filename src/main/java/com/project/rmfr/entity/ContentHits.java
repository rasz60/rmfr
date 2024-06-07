package com.project.rmfr.entity;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.entity.ck.ContentHitsCK;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name="ContentHits")
@Getter
@Setter
@DynamicInsert
public class ContentHits {

    @EmbeddedId
    private ContentHitsCK contentHitsCK;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ancHitsDate;

    public ContentHits () {}

    @Builder
    public ContentHits (AllNoticeContents anc, Members hits) {
        this.contentHitsCK = new ContentHitsCK(anc, hits);
        this.setAncHitsDate(LocalDateTime.now());
    }
}
