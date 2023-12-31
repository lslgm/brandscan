package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.custom.CustomMember;
import com.comeandsee.brandscan.dto.BrandRetouchDTO;
import com.comeandsee.brandscan.service.BrandRetouchService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.comeandsee.brandscan.constants.BrandScanConstant.BRAND_SCAN_USER_MY_PAGE_URL;

@Controller
public class BrandRetouchController {
    private final BrandRetouchService brandRetouchService;

    public BrandRetouchController(BrandRetouchService brandRetouchService) {
        this.brandRetouchService = brandRetouchService;
    }

    @GetMapping("/brand/retouch")
    public String retouchForm(Model model,Long id){
        BrandRetouchDTO brandRetouchDTO = new BrandRetouchDTO();
        model.addAttribute("id",id);
        model.addAttribute("brandRetouchDTO",brandRetouchDTO);
        return "brand/retouch";
    }

    @PostMapping("/brand/retouch")
    public String retouchProc(@Valid @ModelAttribute("brandRetouchDTO") BrandRetouchDTO brandRetouchDTO,
                              BindingResult bindingResult,
                              Authentication authentication) throws Exception {
        if(bindingResult.hasErrors()){
            return "brand/retouch";
        }

        CustomMember member = (CustomMember) authentication.getPrincipal();
        brandRetouchService.register(brandRetouchDTO, member.getEmail());

        return "redirect:" + BRAND_SCAN_USER_MY_PAGE_URL;
    }
}
