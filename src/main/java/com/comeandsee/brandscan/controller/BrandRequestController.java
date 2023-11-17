package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.service.BrandRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BrandRequestController {
    private final BrandRequestService requestService;

    @Autowired
    public BrandRequestController(BrandRequestService requestService) {
        this.requestService = requestService;
    }
    //요청사항 작성페이지 이동
    @GetMapping("/brand/request")
    public String requestFrom(Model model){
        model.addAttribute("requestDTO", new BrandRequestDTO());
        return "brand/request";
    }
    //요청사항 작성페이지 작성
    @PostMapping("/brand/request")
    public String requestProc(@Valid BrandRequestDTO requestDTO,MultipartFile imgFile,
                              BindingResult bindingResult,Model model) throws Exception {

        if (bindingResult.hasErrors()){
            return "brand/request";
        }
        requestService.register(requestDTO, imgFile);
        return "index";
    }
}
