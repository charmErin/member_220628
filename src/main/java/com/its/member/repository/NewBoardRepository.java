package com.its.member.repository;

import com.its.member.entity.NewBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewBoardRepository extends JpaRepository<NewBoardEntity, Long> {
}

