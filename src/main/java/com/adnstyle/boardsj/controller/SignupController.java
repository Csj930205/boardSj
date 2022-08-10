package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final MemberService memberService;

    @PostMapping("signup.do")
    public String signupMember(MemberDto memberDto){
        int result= memberService.signupMember(memberDto);
        if(result>0){
            return "redirect:/";
        }else {
            return "redirect:/signup/signup";
        }
    }
}
