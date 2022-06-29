package com.its.member.controller;

import com.its.member.dto.CommentDTO;
import com.its.member.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService cs;

    @PostMapping("/save")
    public @ResponseBody List<CommentDTO> save(@ModelAttribute CommentDTO commentDTO) {
        cs.save(commentDTO);
        return cs.findAll(commentDTO.getBoardId());
    }

    @DeleteMapping("/{id}")
    public @ResponseBody List<CommentDTO> delete(@PathVariable Long id) {
        cs.delete(id);
        return cs.findAll(id);
    }
}
