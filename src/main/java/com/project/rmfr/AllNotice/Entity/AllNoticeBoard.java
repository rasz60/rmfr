package com.project.rmfr.AllNotice.Entity;

import com.project.rmfr.Member.Entity.Members;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "allNoticeBoard")
@Getter
@Setter
@DynamicInsert
public class AllNoticeBoard {
    // auto-generator를 사용하면 int, float 자료형만 사용 가능, uuid 형식은 binary(16) 사용해야함.
    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")
    private String anbUuid;

    @Column(columnDefinition = "VARCHAR(300)", nullable = false)
    private String anbTitle;

    @Column(columnDefinition = "INT DEFAULT 1", nullable = false)
    private int anbCategory;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime anbRegDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mId", name="anbRegId")
    private Members anbRegId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime anbUpdateDate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(referencedColumnName = "mId", name="anbUpdaterId")
    private Members anbUpdaterId;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int anbState;

    @OneToMany(mappedBy="anbUuid")
    List<AllNoticeContents> anc = new ArrayList<>();

}
