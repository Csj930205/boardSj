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
        /*id 대소문자 구분안하고 id만 같으면 false해야겟네.*/
        int result= memberService.signupMember(memberDto);
        if(result>0){
            return "redirect:/";
        }else {
            return "redirect:/signup/signup";
        }
    }
}
