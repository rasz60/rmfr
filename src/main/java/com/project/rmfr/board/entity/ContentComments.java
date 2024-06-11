package com.project.rmfr.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.rmfr.board.entity.AllNoticeBoard;
import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contentComments")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class ContentComments {

    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")// auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    private String ancCommentUuid;

    @Column(columnDefinition = "VARCHAR(100)")
    private String ancParentCommentUuid;
/*
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "anbUuid")
    private AllNoticeBoard anbUuid;
*/
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String ancComment;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="ancUuid" ,name = "ancUuid")
    private AllNoticeContents ancUuid;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ancCommentRegDate;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "ancCommenterId")
    private Members ancCommenterId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ancCommentUpdateDate;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "ancCommentUpdaterId")
    private Members ancCommentUpdaterId;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int ancCommentDepth;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int ancCommentState;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int sortOrder;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ContentComments parent;


    @OneToMany(mappedBy = "parent")
    private List<ContentComments> children;

    public ContentComments() {}

    @Builder
    public ContentComments(String ancParentCommentUuid, String comment, int sortOrder, AllNoticeContents anc, Members member) {
        this.ancParentCommentUuid = ancParentCommentUuid;
        this.ancComment = comment;
        this.ancUuid = anc;
        this.ancCommentRegDate = LocalDateTime.now();
        this.ancCommentUpdateDate = LocalDateTime.now();
        this.ancCommenterId = member;
        this.ancCommentUpdaterId = member;
        this.sortOrder = sortOrder;
    }

    @Builder
    public ContentComments(String ancParentCommentUuid, String comment, String depth, int sortOrder, AllNoticeContents anc, Members member) {
        this.ancParentCommentUuid = ancParentCommentUuid;
        this.ancComment = comment;
        this.ancUuid = anc;
        this.ancCommentRegDate = LocalDateTime.now();
        this.ancCommentUpdateDate = LocalDateTime.now();
        this.ancCommenterId = member;
        this.ancCommentUpdaterId = member;
        this.ancCommentDepth = Integer.parseInt(depth);
        this.sortOrder = sortOrder;
    }

    public List<ContentComments> getChildren() {
        return children;
    }
}
