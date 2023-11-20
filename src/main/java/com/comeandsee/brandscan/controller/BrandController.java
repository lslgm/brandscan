package com.comeandsee.brandscan.controller;

import com.comeandsee.brandscan.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }


    //생성자주입

    @GetMapping("/brand/result") //스캔결과 맵핑
    public String scanresult(String name,Model model){
        name="BinPoil"; //임시값
        //name="";
        com.comeandsee.brandscan.dto.BrandDTO brandDTO = brandService.scanresult(name);
        model.addAttribute("brandDTO",brandDTO);
        return "/brand/result";
    }

    @GetMapping("/brand/detail") //결과 페이지에서 브랜드 상세보기
    public String branddetail(Long id,Model model){
        com.comeandsee.brandscan.dto.BrandDTO brandDTO=brandService.findOne(id);
        model.addAttribute("brandDTO",brandDTO);
        return "brand/detail";
    }


}
