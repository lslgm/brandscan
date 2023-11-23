package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.BrandDTO;
import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.service.BrandRequestService;
import com.comeandsee.brandscan.service.BrandService;
import com.comeandsee.brandscan.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.comeandsee.brandscan.constants.ManagementConstant.*;

@Log4j2
@RequestMapping(value = MANAGEMENT_BASE_URL)
@Controller
public class ManagementController {
    private final BrandService brandService;
    private final BrandRequestService brandRequestService;
    private final MemberService memberService;

    @Autowired
    public ManagementController(BrandService brandService, BrandRequestService brandRequestService, MemberService memberService) {
        this.brandService = brandService;
        this.brandRequestService = brandRequestService;
        this.memberService = memberService;
    }

    // Main
    @GetMapping()
    public String managementView() {
        return "redirect:" + MANAGEMENT_BASE_URL + "/" + MANAGEMENT_BRAND_LIST_URL;
    }

    // Brand Request
    @GetMapping(value = MANAGEMENT_BRAND_REQUEST_LIST_URL)
    public String brandRequestView(@PageableDefault(page = 1) Pageable pageable, Model model) {
        PageDTO<BrandRequestDTO> brandRequestPage = brandRequestService.findAllWithPage(pageable);

        model.addAttribute("brandRequestPage", brandRequestPage);
        return MANAGEMENT_BRAND_REQUEST_LIST_VIEW;
    }

    @GetMapping(value = MANAGEMENT_BRAND_REQUEST_DETAIL_URL)
    public String brandRequestDetailView(Long id, Model model) {
        BrandRequestDTO brandRequest = brandRequestService.findById(id);

        model.addAttribute("brandRequest", brandRequest);
        return MANAGEMENT_BRAND_REQUEST_DETAIL_VIEW;
    }

    @PostMapping(value = MANAGEMENT_BRAND_REQUEST_DETAIL_URL)
    public String brandRequestUpdateProcess(Long id, int stateCode) {
        brandRequestService.saveStateById(id, stateCode);
        return "redirect:" + MANAGEMENT_BASE_URL + "/" + MANAGEMENT_BRAND_REQUEST_LIST_URL;
    }

    // Brand
    @GetMapping(value = MANAGEMENT_BRAND_LIST_URL)
    public String brandListView(@PageableDefault(page = 1) Pageable pageable, Model model) {
        PageDTO<BrandDTO> brandPage = brandService.findAllWithPage(pageable);

        model.addAttribute("brandPage", brandPage);
        return MANAGEMENT_BRAND_LIST_VIEW;
    }

    @GetMapping(value = MANAGEMENT_BRAND_REGISTER_URL)
    public String brandRegisterView(Model model) {
        model.addAttribute("brand", new BrandDTO());
        return MANAGEMENT_BRAND_REGISTER_VIEW;
    }

    @PostMapping(value = MANAGEMENT_BRAND_REGISTER_URL)
    public String brandRegisterProcess(
            @Valid @ModelAttribute("brand") BrandDTO brandDTO,
            BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return MANAGEMENT_BRAND_REGISTER_VIEW;
        }

        brandService.register(brandDTO);
        return "redirect:" + MANAGEMENT_BASE_URL + "/" + MANAGEMENT_BRAND_LIST_URL;
    }

    @GetMapping(value = MANAGEMENT_BRAND_DETAIL_URL)
    public String brandDetailView(Long id, Model model) {
        BrandDTO brand = brandService.findById(id);

        model.addAttribute("brand", brand);
        return MANAGEMENT_BRAND_DETAIL_VIEW;
    }

    @PostMapping(value = MANAGEMENT_BRAND_DETAIL_URL)
    public String brandDetailProcess(
            @Valid @ModelAttribute("brand") BrandDTO brandDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()) {
            return MANAGEMENT_BRAND_DETAIL_VIEW;
        }

        BrandDTO brand = brandService.updateById(brandDTO);
        redirectAttributes.addAttribute("id", brand.getId());
        redirectAttributes.addFlashAttribute("message", BRAND_UPDATE_SUCCESS_MESSAGE);
        redirectAttributes.addFlashAttribute("redirectUrl", MANAGEMENT_BRAND_LIST_URL);

        return "redirect:" + MANAGEMENT_BASE_URL + "/" + MANAGEMENT_BRAND_DETAIL_URL;
    }

    // Admin member
    @GetMapping(value = MANAGEMENT_MEMBER_LIST_URL)
    public String memberAdminListView(String role, @PageableDefault(page = 1) Pageable pageable, Model model) {
        PageDTO<MemberDTO> memberPage = memberService.findByRoleWithPage(role, pageable);

        model.addAttribute("memberPage", memberPage);
        model.addAttribute("role", role);

        return MANAGEMENT_MEMBER_LIST_VIEW;
    }
}
