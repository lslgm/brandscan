package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/login")
    public String loginFrom(){
        return "user/login";
    }

    //회원가입
    @GetMapping("/user/join")
    public String joinForm(Model model){
        MemberDTO memberDTO = new MemberDTO();
        model.addAttribute("memberDTO",memberDTO);
        return "user/join";
    }
    //회원가입 처리
    @PostMapping("/user/join")
    public String joinProc(@Valid MemberDTO memberDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,Model model) throws Exception {
        if(bindingResult.hasErrors()){
            return "user/join";
        }
        try {
            memberService.saveMember(memberDTO);
            redirectAttributes.addAttribute("errorMessage", "가입이 완료되었습니다.");
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/";
    }
}
