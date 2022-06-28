package com.its.member.entity;

import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "m_table")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(length = 20, unique = true)
    private String memberEmail;

    @Column(length = 10)
    private String memberPassword;

    @Column(length = 10)
    private String memberName;

    @Column(length = 20)
    private String memberMobile;

    @Column(length = 80)
    private String memberProfileName;


    public static Member toSaveEntity(MemberDTO memberDTO) {
        Member member = new Member();
        member.setMemberEmail(memberDTO.getMemberEmail());
        member.setMemberPassword(memberDTO.getMemberPassword());
        member.setMemberName(memberDTO.getMemberName());
        member.setMemberMobile(memberDTO.getMemberMobile());
        member.setMemberProfileName(memberDTO.getMemberProfileName());
        return member;
    }

}
