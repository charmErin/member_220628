package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.BoardService;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class TestClass {
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
//    @DisplayName("member_save 테스트")
//    public void memberSaveTest() throws IOException {
//        Long id = ms.save(newMember(1));
//        assertThat(ms.findById(id)).isNotNull();
//    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("member_login 테스트")
    public void member_LoginTest() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("admin");
        memberDTO.setMemberPassword("admin");
        assertThat(ms.loginCheck(memberDTO)).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("member_findById 테스트")
    public void memberFindByIdTest() {
        assertThat(ms.findById(1L)).isNotNull();
    }

//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("mmeber_update 테스트")
//    public void memberUpdateTest() throws IOException {
//        MemberDTO memberDTO = ms.findById(1L);
//        memberDTO.setMemberMobile("010-1234-5678");
//        ms.update(memberDTO);
//        assertThat(memberDTO.getMemberMobile()).isNotEqualTo(ms.findById(1L).getMemberMobile());
//    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("member_delete 테스트")
    public void memberDeleteTest() {
        ms.delete(1L);
        assertThat(ms.findById(1L)).isNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("member_findAll 테스트")
    public void memberFindAllTest() {
        // 현재 회원수는 2명
        assertThat(ms.findAll().size()).isEqualTo(2);
    }


    // Board 테스트
    @Autowired
    private BoardService bs;

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("board_findById 테스트")
    public void boardFindByIdTest() {
        assertThat(bs.findById(1L)).isNotNull();
    }

}
