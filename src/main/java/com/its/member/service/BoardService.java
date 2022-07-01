package com.its.member.service;

import com.its.member.common.PagingConst;
import com.its.member.dto.BoardDTO;
import com.its.member.entity.Board;
import com.its.member.entity.Member;
import com.its.member.repository.BoardRepository;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public BoardDTO findById(Long id) {
        br.hitsAdd(id);
        Optional<Board> optionalBoard = br.findById(id);
        if (optionalBoard.isPresent()) {
            return BoardDTO.toBoardDTO(optionalBoard.get());
        } else {
            return null;
        }
    }

    public Page<BoardDTO> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();

        page = (page==1) ? 0 : (page-1);

        Page<Board> boardList = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        Page<BoardDTO> boardDTOList = boardList.map(
                board -> new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
                                        board.getBoardContents(), board.getBoardHits(), board.getCreatedTime(),
                                        board.getUpdatedTime(), board.getBoardFileName())
        );
        return boardDTOList;
    }

    public void delete(Long id) {
        br.deleteById(id);
    }

    public void update(BoardDTO boardDTO) throws IOException {
        MultipartFile updateFile = boardDTO.getBoardFile();
        if (!updateFile.isEmpty()) {
            String updateFileName = updateFile.getOriginalFilename();
            updateFileName = System.currentTimeMillis() + "_" + updateFileName;
            String updatePath = "D:\\springboot_img\\" + updateFileName;
            updateFile.transferTo(new File(updatePath));
            boardDTO.setBoardFileName(updateFileName);
        }
        Member member = mr.findByMemberEmail(boardDTO.getBoardWriter()).get();
        br.save(Board.toUpdateEntity(boardDTO, member));
    }

    public Page<BoardDTO> search(Pageable pageable, String choice, String q) {
        int page = pageable.getPageNumber();

        page = (page==1) ? 0 : (page-1);

        if (choice.equals("boardTitle")) {
            Page<Board> boardList = br.findByBoardTitleContaining(q, PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<BoardDTO> boardDTOList = boardList.map(
                    board -> new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
                            board.getBoardContents(), board.getBoardHits(), board.getCreatedTime(),
                            board.getUpdatedTime(), board.getBoardFileName())
            );
            return boardDTOList;
        } else if(choice.equals("boardWriter")) {
            Page<Board> boardList = br.findByBoardWriterContaining(q, PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
            Page<BoardDTO> boardDTOList = boardList.map(
                    board -> new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
                            board.getBoardContents(), board.getBoardHits(), board.getCreatedTime(),
                            board.getUpdatedTime(), board.getBoardFileName())
            );
            return boardDTOList;
        } else {
            return null;
        }

    }

    public Page<BoardDTO> hitsDesc(Pageable pageable) {
        int page = pageable.getPageNumber();

        page = (page==1) ? 0 : (page-1);

        Page<Board> boardList = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "boardHits")));
        Page<BoardDTO> boardDTOList = boardList.map(
                board -> new BoardDTO(board.getId(), board.getBoardTitle(), board.getBoardWriter(),
                        board.getBoardContents(), board.getBoardHits(), board.getCreatedTime(),
                        board.getUpdatedTime(), board.getBoardFileName())
        );
        return boardDTOList;
    }
}
