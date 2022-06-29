package com.its.member.dto;

import com.its.member.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentCreatedTime;




    public CommentDTO(Long boardId, String commentWriter, String commentContents, LocalDateTime commentCreatedTime) {
        this.boardId = boardId;
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
        this.commentCreatedTime = commentCreatedTime;
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setCommentWriter(comment.getCommentWriter());
        commentDTO.setCommentContents(comment.getCommentContents());
        commentDTO.setCommentCreatedTime(comment.getCreatedTime());
        return commentDTO;
    }
}
