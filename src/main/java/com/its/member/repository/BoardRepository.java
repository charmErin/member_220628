package com.its.member.repository;

import com.its.member.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("update Board b set b.boardHits=b.boardHits+1 where b.id= :boardId")
    void hitsAdd(@Param("boardId") Long boardId);
}
