package com.adnstyle.boardsj.controller;

import com.adnstyle.boardsj.dto.MemberDto;
import com.adnstyle.boardsj.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.lang.reflect.Member;
import java.util.Map;

@Controller
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final MemberService memberService;

    /*
     * 일반가입에자에 대한 처리
     * */
    @PostMapping("signup.do")
    public String signupMember(MemberDto memberDto) {
//        if (errors.hasErrors()) {
//            // 회원가입 실패시, 입력 데이터 유지
//            model.addAttribute("memberDto", memberDto);
//            // 유효성 통과 못한 필드와 메세지를 핸들링
//            Map<String, String> validatorResult = memberService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
//            return "redirect:/signup/signup.do";
//        }

        // 회원가입 처리
        int result = memberService.signupMember(memberDto);
        if (result > 0) {
            return "redirect:/";
        } else {
            return "redirect:/signup/signup.do";
        }
    }

}
