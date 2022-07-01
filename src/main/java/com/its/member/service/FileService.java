package com.its.member.service;

import com.its.member.dto.FileDTO;
import com.its.member.entity.FileEntity;
import com.its.member.entity.NewBoardEntity;
import com.its.member.repository.FileRepository;
import com.its.member.repository.NewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final NewBoardRepository boardFileRepository;

    public FileDTO save(FileDTO fileDTO) throws IOException {
        MultipartFile boardFile = fileDTO.getBoardFile();

        if(!boardFile.isEmpty()){
            String boardFileName = boardFile.getOriginalFilename();
            boardFileName = System.currentTimeMillis()+ "_" + boardFileName;
            String savePath = "D:\\springboot_img\\" + boardFileName;
            boardFile.transferTo(new File(savePath));
            fileDTO.setBoardFileName(boardFileName);
        }
        NewBoardEntity newBoardEntity = boardFileRepository.findById(fileDTO.getBoardId()).get();
        Long id = fileRepository.save(FileEntity.toSaveEntity(fileDTO, newBoardEntity)).getId();
        return FileDTO.toDTO(fileRepository.findById(id).get());
    }

//    public List<FileDTO> findAll(FileDTO fileDTO) {
//        NewBoardEntity boardFile = boardFileRepository.findById(fileDTO.getBoardId()).get();
//        List<FileEntity> fileList = boardFile.getFileEntityList();
//        List<FileDTO> fileDTOList = new ArrayList<>();
//        for (FileEntity file: fileList) {
//            fileDTOList.add(FileDTO.toDTO(file));
//        }
//        return fileDTOList;
//    }
}
