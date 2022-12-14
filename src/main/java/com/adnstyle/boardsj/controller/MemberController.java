package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.dto.User;
import com.adnstyle.boardsj.repository.UserRepository;
import com.adnstyle.boardsj.service.CustomOAuth2UserService;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final CustomOAuth2UserService customOAuth2UserService;

    /*
     * 회원리스트 조회(소셜회원 리스트 추가)
     * */
    @GetMapping("list.do")
    public String listMember(Model model) {
        //일반가입자 리스트
        List<MemberDto> listMember = memberService.listMember();
        model.addAttribute("listMember", listMember);

        //소셜가입자 리스트
        List<User> listUser = customOAuth2UserService.listUser();
        model.addAttribute("listUser", listUser);

        return "member/list";
    }

    /*
     * 회원정보 수정
     * */
    @PostMapping("update.do")
    public String updateMember(MemberDto memberDto) {
        int result = memberService.updateMember(memberDto);
        if (result > 0) {
            return "redirect:/member/list.do";
        } else {
            return "redirect:/";
        }
    }

    /*
     * 회원삭제
     * */
    @GetMapping("/delete/{seq}")
    public String deleteMember(@PathVariable("seq") int seq) {
        int result = memberService.deleteMember(seq);
        if (result > 0) {
            return "redirect:/member/list.do";
        } else {
            return "redirect:/member/modify/{id}";
        }
    }
}
