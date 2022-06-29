package com.its.member.service;

import com.its.member.dto.BoardDTO;
import com.its.member.entity.Board;
import com.its.member.entity.Member;
import com.its.member.repository.BoardRepository;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository br;
    private final MemberRepository mr;


    public Long save(BoardDTO boardDTO) throws IOException {
        MultipartFile boardFile = boardDTO.getBoardFile();
        if (!boardFile.isEmpty()) {
            String boardFileName = boardFile.getOriginalFilename();
            boardFileName = System.currentTimeMillis() + "_" + boardFileName;
            String savePath = "D:\\springboot_img\\" + boardFileName;
            boardFile.transferTo(new File(savePath));
            boardDTO.setBoardFileName(boardFileName);
        }
        Member member = mr.findByMemberEmail(boardDTO.getBoardWriter()).get();
        return br.save(Board.toSaveEntity(boardDTO, member)).getId();
    }

    @Transactional
    public BoardDTO findById(Long boardId) {
        br.hitsAdd(boardId);
        Optional<Board> optionalBoard = br.findById(boardId);
        if (optionalBoard.isPresent()) {
            return BoardDTO.toBoardDTO(optionalBoard.get());
        } else {
            return null;
        }
    }
}
