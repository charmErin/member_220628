package com.its.member.controller;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService ms;

    @GetMapping("/save-form")
    public String saveForm() {
        return "member/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        ms.save(memberDTO);
        return "member/login";
    }

    @PostMapping("/dup-check")
    public @ResponseBody String dupIdCheck(@RequestParam String memberEmail) {
        MemberDTO dupResult = ms.findByMemberEmail(memberEmail);
        if (dupResult == null) {
            return "ok";
        } else {
            return "no";
        }
    }


    @GetMapping("/login-form")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginDTO = ms.loginCheck(memberDTO);
        if (loginDTO != null) {
            session.setAttribute("loginId", loginDTO.getId());
            session.setAttribute("loginEmail", loginDTO.getMemberEmail());
            return "redirect:/board";
        } else {
            return "member/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("member", ms.findById(id));
        return "member/detail";
    }

    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("member", ms.findById(id));
        return "member/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, Model model) throws IOException {
        ms.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        ms.delete(id);
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail.equals("관리자")) {
            return "redirect:/member/";
        } else {
            session.invalidate();
            return "index";
        }
    }

    // 관리자 페이지
    @GetMapping("/admin")
    public String adminPage() {
        return "member/admin";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        model.addAttribute("memberList", ms.findAll());
        return "member/list";
    }


    // 카카오 로그인
    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code, HttpSession session) {
        System.out.println("code : " + code);

        String access_Token = ms.kakaoLogin(code);
        System.out.println("access_Token : " + access_Token);

        HashMap<String, Object> userInfo = ms.createKakaoUser(access_Token);
        System.out.println("login Controller : " + userInfo);

        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("access_Token", access_Token);
        }

        return "index";
    }

    // 카카오 로그아웃
    @GetMapping("/kakao/logout")
    public String kakaoLogout(HttpSession session) {
        String access_Token = (String)session.getAttribute("access_Token");

        if(access_Token != null && !"".equals(access_Token)){
            ms.kakaoLogout(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("userId");
        }else{
            System.out.println("access_Token is null");
            //return "redirect:/";
        }
        return "index";
    }


}
