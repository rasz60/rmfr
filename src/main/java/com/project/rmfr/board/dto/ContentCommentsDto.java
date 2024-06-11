package com.project.rmfr.board.dto;

import com.project.rmfr.board.entity.ContentComments;
import com.project.rmfr.member.dto.MembersDto;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ContentCommentsDto {

    private String ancCommentUuid;
    private String ancParentCommentUuid;
    private String ancComment;
    private String ancUuid;
    private LocalDateTime ancCommentRegDate;
    private MembersDto ancCommenterId;
    private LocalDateTime ancCommentUpdateDate;
    private MembersDto ancCommentUpdaterId;
    private int ancCommentDepth;
    private int ancCommentState;

    private boolean commentEditable = false;

    public ContentCommentsDto() {}

    public static ContentCommentsDto of(ContentComments comments) {
        return new ContentCommentsDto(comments);
    }

    public static List<ContentCommentsDto> of(List<ContentComments> commentsList) {
        List<ContentCommentsDto> dtos = new ArrayList<ContentCommentsDto>();
        for ( ContentComments comment : commentsList ) {
            dtos.add(new ContentCommentsDto(comment));
        }
        return dtos;
    }

    public ContentCommentsDto(ContentComments comments) {
        this.ancCommentUuid = comments.getAncCommentUuid();
        this.ancParentCommentUuid = comments.getAncParentCommentUuid();
        this.ancComment = comments.getAncComment();
        this.ancUuid = comments.getAncUuid().getAncUuid();
        this.ancCommentRegDate = comments.getAncCommentRegDate();
        this.ancCommenterId = MembersDto.of(comments.getAncCommenterId(), 1);
        this.ancCommentUpdateDate = comments.getAncCommentUpdateDate();
        this.ancCommentUpdaterId = MembersDto.of(comments.getAncCommenterId(), 1);
        this.ancCommentDepth = comments.getAncCommentDepth();
        this.ancCommentState = comments.getAncCommentState();
    }

    public void setCommentEditable(String mId) {
        this.commentEditable = mId.equals(this.ancCommenterId.getMId());
    }
}
