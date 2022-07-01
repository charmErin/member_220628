package com.its.member.entity;

import com.its.member.dto.FileDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String boardFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="new_board_id")
    private NewBoardEntity newBoardEntity;


    public static FileEntity toSaveEntity(FileDTO fileDTO, NewBoardEntity boardFile) {
        FileEntity file = new FileEntity();
        file.setBoardFileName(fileDTO.getBoardFileName());
        file.setNewBoardEntity(boardFile);
        return file;
    }
}
