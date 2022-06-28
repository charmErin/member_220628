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



}
