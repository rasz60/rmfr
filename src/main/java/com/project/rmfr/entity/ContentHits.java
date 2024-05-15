package com.project.rmfr.entity;

import com.project.rmfr.entity.ck.ContentHitsCK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="ContentHits")
@Getter
@Setter
public class ContentHits {

    @EmbeddedId
    private ContentHitsCK contentHitsCK;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ancHitsDate;
}
