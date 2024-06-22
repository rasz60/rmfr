package com.project.rmfr.menu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.rmfr.member.entity.Members;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "rmfrMenus")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class RmfrMenus {

    @Id
    @GeneratedValue(generator="uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "VARCHAR(100)")
    private String rmfrMenuUuid;

    @Column(columnDefinition = "VARCHAR(100)")
    private String menuName;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String menuLink;

    @Column(columnDefinition = "VARCHAR(100)")
    private String menuIcon;

    @Column(columnDefinition = "VARCHAR(1) DEFAULT 'Y'")
    private String menuOpenYn;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int menuLevel;

    @Column(columnDefinition = "INT")
    private int sortOrder;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "menuRegId")
    private Members menuRegId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime menuRegDate;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(referencedColumnName="mEntrId" ,name = "menuUpdaterId")
    private Members menuUpdaterId;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime menuUpdateDate;

    public RmfrMenus() {}

    public RmfrMenus(String menuName, String menuLink, String menuIcon, int menuLevel, int sortOrder) {
        this.menuName = menuName;
        this.menuLink = menuLink;
        this.menuIcon = menuIcon;
        this.menuLevel = menuLevel;
        this.sortOrder = sortOrder;
    }

}
