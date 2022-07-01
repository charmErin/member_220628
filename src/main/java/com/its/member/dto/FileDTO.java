package com.its.member.dto;

import com.its.member.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private Long id;
    private Long boardId;
    private MultipartFile boardFile;
    private String boardFileName;


    public static FileDTO toDTO(FileEntity file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setBoardFileName(file.getBoardFileName());
        return fileDTO;
    }
}
