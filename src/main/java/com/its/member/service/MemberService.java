package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.Member;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository mr;


    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberFile = memberDTO.getMemberProfile();
        String memberFileName = memberFile.getOriginalFilename();
        memberFileName = System.currentTimeMillis() + "_" + memberFileName;
        String savePath = "D:\\springboot_img\\" + memberFileName;
        if (!memberFile.isEmpty()) {
            memberFile.transferTo(new File(savePath));
            memberDTO.setMemberProfileName(memberFileName);
        }
        Long saveId = Member.toSaveEntity(memberDTO).getId();
        return saveId;
    }
}
