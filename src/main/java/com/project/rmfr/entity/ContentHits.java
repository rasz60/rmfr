package com.project.rmfr.entity;

import com.project.rmfr.entity.ck.ContentHitsCK;
import jakarta.persistence.*;
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
}
