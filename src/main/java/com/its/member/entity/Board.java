package com.its.member.entity;

import com.its.member.dto.BoardDTO;
import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="b_table")
public class Board extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String boardTitle;

    @Column(length = 20)
    private String boardWriter;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column(length = 80)
    private String boardFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public static Board toSaveEntity(BoardDTO boardDTO, Member member) {
        Board board = new Board();
        board.setBoardTitle(boardDTO.getBoardTitle());
        board.setBoardWriter(boardDTO.getBoardWriter());
        board.setBoardContents(boardDTO.getBoardContents());
        board.setBoardHits(0);
        board.setBoardFileName(boardDTO.getBoardFileName());
        board.setMember(member);
        return board;
    }

}
