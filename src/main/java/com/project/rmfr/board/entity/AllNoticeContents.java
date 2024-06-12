package com.project.rmfr.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allNoticeContents")
@Getter
@Setter
@DynamicInsert
public class AllNoticeContents {
    // Contents 테이블
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")// auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    private String ancUuid;
/*
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "anbUuid")
    private AllNoticeBoard anbUuid;
*/
    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    private String ancTitle;

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable=false)
    private String ancContents;

    @Column(columnDefinition = "VARCHAR(300)")
    private String ancKw;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ancRegDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "ancRegId")
    private Members ancRegId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ancUpdateDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "ancUpdaterId")
    private Members ancUpdaterId;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int ancState;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int ancAuth;

    @OneToMany(mappedBy = "contentHitsCK.ancUuid", fetch = FetchType.EAGER)
    List<ContentHits> hits = new ArrayList<>();

    @OneToMany(mappedBy = "contentLikesCK.contentId", fetch = FetchType.EAGER)
    List<ContentLikes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "ancUuid", fetch = FetchType.EAGER)
    @OrderBy("SORT_ORDER ASC")
    List<ContentComments> comments = new ArrayList<>();
    public AllNoticeContents() {}

    @Builder
    public AllNoticeContents(String ancTitle, String ancKw, String ancContents, Members member) {
        this.ancTitle = ancTitle;
        this.ancKw = ancKw;
        this.ancContents = ancContents;
        this.ancRegId = member;
        this.ancUpdaterId = member;
        this.ancState = 2;
    }
}
