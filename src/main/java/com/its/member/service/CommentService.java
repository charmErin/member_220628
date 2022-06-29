package com.its.member.service;

import com.its.member.dto.CommentDTO;
import com.its.member.dto.MemberDTO;
import com.its.member.entity.Board;
import com.its.member.entity.Comment;
import com.its.member.entity.Member;
import com.its.member.repository.BoardRepository;
import com.its.member.repository.CommentRepository;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository cr;
    private final MemberRepository mr;
    private final BoardRepository br;


    public void save(CommentDTO commentDTO) {
        Member member = mr.findByMemberEmail(commentDTO.getCommentWriter()).get();
        Board board = br.findById(commentDTO.getBoardId()).get();
        cr.save(Comment.toSaveEntity(commentDTO, member, board));
    }

    public List<CommentDTO> findAll(Long board_id) {
        List<Comment> commentList = cr.findByBoardId(board_id);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment c: commentList) {
            commentDTOList.add(CommentDTO.toCommentDTO(c));
        }
        return commentDTOList;
    }

    public void delete(Long id) {
        cr.deleteById(id);
    }
}
