package com.its.member.entity;

import com.its.member.dto.NewBoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class NewBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_board_id")
    private Long id;
    @Column
    private String boardTitle;
    @Column
    private String boardWriter;
    @Column
    private String boardContents;

    @OneToMany(mappedBy = "newBoardEntity", cascade = CascadeType.ALL,orphanRemoval = true ,fetch=FetchType.LAZY)
    List<FileEntity> fileEntityList= new ArrayList<>();

    public static NewBoardEntity toSaveEntity(NewBoardDTO boardFileDTO) {
        NewBoardEntity boardfile = new NewBoardEntity();
        boardfile.setBoardTitle(boardFileDTO.getBoardTitle());
        boardfile.setBoardWriter(boardFileDTO.getBoardWriter());
        boardfile.setBoardContents(boardFileDTO.getBoardContents());
        return boardfile;
    }

}
