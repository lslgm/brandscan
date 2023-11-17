package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.dto.BrandDTO;
import com.comeandsee.brandscan.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class brandController {
    private final BrandService brandService;


    //생성자주입
    @Autowired
    public brandController(BrandService service) {
        this.brandService = service;

    }

    @GetMapping("/brand/scanresult") //스캔결과 맵핑
    public String scanresult(String name,Model model){
        name="나이키"; //임시값
        BrandDTO brandDTO = brandService.scanresult(name);
        model.addAttribute("brandDTO",brandDTO);
        return "/brand/scanresult";
    }

    @GetMapping("/brand/detail") //결과 페이지에서 브랜드 상세보기
    public String branddetail(Long id,Model model){
        BrandDTO brandDTO=brandService.findOne(id);
        model.addAttribute("brandDTO",brandDTO);
        return "brand/detail";
    }


}
