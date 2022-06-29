package com.its.member.dto;

import com.its.member.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
    private MultipartFile boardFile;
    private String boardFileName;

    public static BoardDTO toBoardDTO(Board board) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(board.getId());
        boardDTO.setBoardTitle(board.getBoardTitle());
        boardDTO.setBoardWriter(board.getBoardWriter());
        boardDTO.setBoardContents(board.getBoardContents());
        boardDTO.setBoardHits(board.getBoardHits());
        boardDTO.setBoardFileName(board.getBoardFileName());
        boardDTO.setBoardCreatedTime(board.getCreatedTime());
        boardDTO.setBoardUpdatedTime(board.getUpdatedTime());
        return boardDTO;
    }
}
