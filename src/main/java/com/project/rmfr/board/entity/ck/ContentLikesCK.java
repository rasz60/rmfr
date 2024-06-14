package com.project.rmfr.board.entity.ck;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Data
public class ContentLikesCK implements Serializable {

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String contentId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mEntrId", name="contentLikerId")
    private Members contentLikerId;

    public ContentLikesCK() {}
}
