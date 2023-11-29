package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.custom.CustomMember;
import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.service.BrandRequestService;
import com.comeandsee.brandscan.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.comeandsee.brandscan.constants.BrandScanConstant.BRAND_SCAN_MY_PAGE_VIEW;
import static com.comeandsee.brandscan.constants.BrandScanConstant.BRAND_SCAN_USER_MY_PAGE_URL;

@Log4j2
@Controller
public class MemberController {
    private final MemberService memberService;
    private final BrandRequestService brandRequestService;

    @Autowired
    public MemberController(MemberService memberService, BrandRequestService brandRequestService) {
        this.memberService = memberService;
        this.brandRequestService = brandRequestService;
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
    public String joinProc(
            @Valid @ModelAttribute("memberDTO") MemberDTO memberDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) throws Exception
    {
        if(bindingResult.hasErrors()){
            return "user/join";
        }

        if (!memberService.validateDuplicateMember(memberDTO.getEmail())) {
            model.addAttribute("errorMessage", "이미 가입된 이메일 입니다.");
            return "user/join";
        }

        memberService.saveMember(memberDTO);
        model.addAttribute("infoMessage", memberDTO.getName() + "님, 가입을 환영합니다.");

        return "user/join";
    }

    // 마이 페이지
    @GetMapping(BRAND_SCAN_USER_MY_PAGE_URL)
    public String myPageView(
            Authentication authentication,
            @PageableDefault(page = 1) Pageable pageable,
            Model model) throws Exception {
        CustomMember member = (CustomMember) authentication.getPrincipal();
        String email = member.getEmail();
        PageDTO<BrandRequestDTO> brandRequestPage = brandRequestService.findAllByMember(email, pageable);

        model.addAttribute("brandRequestPage", brandRequestPage);
        model.addAttribute("email", email);
        model.addAttribute("createdAt", member.getCreatedAt());

        return BRAND_SCAN_MY_PAGE_VIEW;
    }
}
