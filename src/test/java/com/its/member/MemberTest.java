package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService ms;

//    public MemberDTO newMember(int i) {
//        MemberDTO memberDTO = new MemberDTO("email"+i, "pw"+i, "name"+i, "mobile"+i);
//        return memberDTO;
//    }
//
//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("save 테스트")
//    public void saveTest() throws IOException {
//        Long id = ms.save(newMember(1));
//        assertThat(ms.findById(id)).isNotNull();
//    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("login 테스트")
    public void loginTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("admin");
        memberDTO.setMemberPassword("admin");
        assertThat(ms.loginCheck(memberDTO)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findById 테스트")
    public void findByIdTest() {
        assertThat(ms.findById(1L)).isNotNull();
    }

//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("update 테스트")
//    public void updateTest() throws IOException {
//        MemberDTO memberDTO = ms.findById(1L);
//        memberDTO.setMemberMobile("010-1234-5678");
//        ms.update(memberDTO);
//        assertThat(memberDTO.getMemberMobile()).isNotEqualTo(ms.findById(1L).getMemberMobile());
//    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("delete 테스트")
    public void deleteTest() {
        ms.delete(1L);
        assertThat(ms.findById(1L)).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("findAll 테스트")
    public void findAllTest() {
        // 현재 회원수는 2명
        assertThat(ms.findAll().size()).isEqualTo(2);
    }


}
