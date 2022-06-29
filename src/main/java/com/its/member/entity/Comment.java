package com.its.member.entity;

import com.its.member.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="c_table")
public class Comment extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(length = 20)
    private String commentWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 100)
    private String commentContents;


    public static Comment toSaveEntity(CommentDTO commentDTO, Member member, Board board) {
        Comment comment = new Comment();
        comment.setCommentWriter(commentDTO.getCommentWriter());
        comment.setCommentContents(commentDTO.getCommentContents());
        comment.setMember(member);
        comment.setBoard(board);
        return comment;
    }
}
