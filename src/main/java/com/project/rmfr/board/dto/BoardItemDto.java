package com.project.rmfr.board.dto;

import com.project.rmfr.board.entity.AllNoticeContents;
import com.project.rmfr.board.entity.ContentComments;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardItemDto {
    private String ancUuid;
    private String ancTitle;
    private String ancContents;
    private String ancRegId;
    private LocalDateTime ancRegDate;
    private String ancUpdaterId;
    private LocalDateTime ancUpdateDate;
    private String ancKw;
    private List<ContentCommentsDto> ancComments;
    private int hits;
    private int likes;
    private boolean visible = true;
    private boolean editable = false;
    private boolean deletable = false;
    private boolean likeItem = false;
    private boolean commentable = false;

    public BoardItemDto() {}

    public static BoardItemDto of(AllNoticeContents anc) {
        return new BoardItemDto(anc);
    }

    public BoardItemDto(AllNoticeContents anc) {
        this.ancUuid = anc.getAncUuid();
        this.ancTitle = anc.getAncTitle();
        this.ancContents = anc.getAncContents();
        this.ancRegId = anc.getAncRegId().getMId();
        this.ancRegDate = anc.getAncRegDate();
        this.ancUpdaterId = anc.getAncUpdaterId().getMId();
        this.ancUpdateDate = anc.getAncUpdateDate();
        this.ancComments = ContentCommentsDto.of(anc.getComments());
        this.ancKw = anc.getAncKw();
        this.hits = anc.getHits().size();
        this.likes = anc.getLikes().size();
    }
}
