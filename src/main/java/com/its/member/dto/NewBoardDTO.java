package com.its.member.dto;

import com.its.member.entity.NewBoardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private List<FileDTO> boardFileList;

    public static NewBoardDTO toDTO(NewBoardEntity boardFile) {
        NewBoardDTO boardFileDTO = new NewBoardDTO();
        boardFileDTO.setId(boardFile.getId());
        boardFileDTO.setBoardTitle(boardFile.getBoardTitle());
        boardFileDTO.setBoardWriter(boardFile.getBoardWriter());
        boardFileDTO.setBoardContents(boardFile.getBoardContents());
        return boardFileDTO;
    }
}
