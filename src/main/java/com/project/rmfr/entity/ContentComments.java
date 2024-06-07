package com.project.rmfr.entity;

import com.project.rmfr.board.entity.AllNoticeBoard;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "contentComments")
@Getter
@Setter
public class ContentComments {

    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")// auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    private String ancCommentUuid;

    @Column(columnDefinition = "BINARY(16)")
    private String ancParentCommentUuid;
/*
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "anbUuid")
    private AllNoticeBoard anbUuid;
*/
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "ancUuid")
    private AllNoticeContents ancUuid;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ancCommentRegDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mId" ,name = "ancCommenterId")
    private Members ancCommenterId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime ancCommentUpdateDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mId" ,name = "ancCommentUpdaterId")
    private Members ancCommentUpdaterId;

    @Column(columnDefinition = "INT DEFAULT 1", nullable = false)
    private int ancCommentDepth;

    @Column(columnDefinition = "INT DEFAULT 1", nullable = false)
    private int ancCommentState;

    public ContentComments() {}
}
