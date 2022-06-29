package com.its.member.repository;

import com.its.member.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("update Board b set b.boardHits=b.boardHits+1 where b.id= :id")
    void hitsAdd(@Param("id") Long id);

    Page<Board> findByBoardTitleContaining(String q, Pageable pageable);
    Page<Board> findByBoardWriterContaining(String q, Pageable pageable);
}
