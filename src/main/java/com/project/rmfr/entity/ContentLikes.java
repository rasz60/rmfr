package com.project.rmfr.entity;

import com.project.rmfr.entity.ck.ContentLikesCK;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="contentLikes")
@Getter
public class ContentLikes {

    @EmbeddedId
    private ContentLikesCK contentLikesCK;

    @Column(columnDefinition = "VARCHAR(10)", nullable = false)
    private String contentType;
}
