package com.its.member.repository;

import com.its.member.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
