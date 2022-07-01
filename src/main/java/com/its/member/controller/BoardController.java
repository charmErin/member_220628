package com.its.member.controller;

import com.its.member.common.PagingConst;
import com.its.member.dto.BoardDTO;
import com.its.member.dto.FileDTO;
import com.its.member.dto.NewBoardDTO;
import com.its.member.service.BoardService;
import com.its.member.service.CommentService;
import com.its.member.service.FileService;
import com.its.member.service.NewBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService bs;
    private final CommentService cs;

    @GetMapping("/save-form")
    public String saveForm() {
        return "board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, Model model) throws IOException {
        Long boardId = bs.save(boardDTO);
        model.addAttribute("board", bs.findById(boardId));
        return "board/detail";
    }

    @GetMapping
    public String findAll(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = bs.findAll(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/list";
    }

    @GetMapping("/detail")
    public String findById(@RequestParam(value="page", defaultValue = "1") int page,
                           @RequestParam Long id, Model model) {
        model.addAttribute("board", bs.findById(id));
        model.addAttribute("page", page);
        model.addAttribute("commentList", cs.findAll(id));
        return "board/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        bs.delete(id);
        return "redirect:/board";
    }

    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", bs.findById(id));
        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO) throws IOException {
        bs.update(boardDTO);
        return "redirect:/board/detail?id=" + boardDTO.getId();
    }

    @GetMapping("/search")
    public String search(@PageableDefault(page = 1) Pageable pageable,
                         @RequestParam String choice,
                         @RequestParam String q, Model model) {
        System.out.println("choice: " + choice);
        System.out.println("q: " + q);
        Page<BoardDTO> boardList = bs.search(pageable, choice, q);

        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("choice", choice);
        model.addAttribute("q", q);
        return "board/searchList";
    }

    @GetMapping("/hitsDesc")
    public String hitsDesc(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boardList = bs.hitsDesc(pageable);
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/listHitsDesc";
    }

//    private final NewBoardService boardFileService;
//    private final FileService fileService;
//
//    @GetMapping("/fileSave-form")
//    public String fileSaveForm() {
//        return "board/fileSave";
//    }
//    @PostMapping("/fileSave")
//    public String fileSave(@ModelAttribute NewBoardDTO boardFileDTO,
//                           MultipartHttpServletRequest mp,
//                           Model model) throws IOException {
//        NewBoardDTO saveDTO = boardFileService.fileSave(boardFileDTO);
//        List<MultipartFile> multipartFileList = mp.getFiles("boardFile");
//
//        List<FileDTO> fileDTOList = new ArrayList<>();
//        for (MultipartFile m: multipartFileList) {
//            FileDTO fileDTO = new FileDTO();
//            fileDTO.setBoardId(saveDTO.getId());
//            fileDTO.setBoardFile(m);
//            fileDTOList.add(fileService.save(fileDTO));
//        }
//        saveDTO.setBoardFileList(fileDTOList);
//        model.addAttribute("boardDTO", saveDTO);
////        saveDTO.getBoardDTOList.get(i);
////        model.addAttribute("fileDTOList", fileDTOList);
//        return "board/detail";
//    }

}
