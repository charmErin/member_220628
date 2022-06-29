package com.its.member.controller;

import com.its.member.common.PagingConst;
import com.its.member.dto.BoardDTO;
import com.its.member.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService bs;

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
        return "board/detail";
    }

}
