package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import com.comeandsee.brandscan.service.BrandRequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.comeandsee.brandscan.constants.ManagementConstant.*;

@Log4j2
@RequestMapping(value = MANAGEMENT_BASE_URL)
@Controller
public class ManagementController {
    private final BrandRequestService brandRequestService;

    @Autowired
    public ManagementController(BrandRequestService brandRequestService) {
        this.brandRequestService = brandRequestService;
    }

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
}
