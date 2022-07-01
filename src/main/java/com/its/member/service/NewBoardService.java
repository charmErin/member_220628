package com.its.member.service;

import com.its.member.dto.NewBoardDTO;
import com.its.member.entity.NewBoardEntity;
import com.its.member.repository.NewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewBoardService {
    private final NewBoardRepository boardFileRepository;

    public NewBoardDTO fileSave(NewBoardDTO boardFileDTO) {
        Long id = boardFileRepository.save(NewBoardEntity.toSaveEntity(boardFileDTO)).getId();
        return NewBoardDTO.toDTO(boardFileRepository.findById(id).get());
    }
}