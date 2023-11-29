package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/user/login")
    public String loginFrom() {
        return "user/login";
    }

    //회원가입
    @GetMapping("/user/join")
    public String joinForm(Model model) {
        MemberDTO memberDTO = new MemberDTO();
        model.addAttribute("memberDTO", memberDTO);
        return "user/join";
    }

    //회원가입 처리
    @PostMapping("/user/join")
    public String joinProc(@Valid @ModelAttribute("memberDTO") MemberDTO memberDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes, Model model) throws Exception {

       /* bindingResult.addError(new FieldError("memberDTO","emailerror","중복된 이메일 입니다." ));*/
        if (bindingResult.hasErrors()) {
            return "user/join";
        }
        try {
            memberService.saveMember(memberDTO);
            redirectAttributes.addAttribute("errorMessage", null);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", "중복된 이메일 입니다.");
            return "user/join";
        }

        return "redirect:/";
    }

}
