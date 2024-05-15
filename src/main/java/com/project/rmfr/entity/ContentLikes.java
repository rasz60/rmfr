package com.project.rmfr.entity;

import com.project.rmfr.entity.ck.ContentLikesCK;
import jakarta.persistence.*;
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
}
