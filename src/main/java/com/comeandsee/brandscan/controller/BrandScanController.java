package com.comeandsee.brandscan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandScanController {
    @GetMapping("/")
    public String indexView() {
        return "redirect:/brandscan";
    }

    @GetMapping("/brandscan")
    public String brandScanView() {
        return "contents/brandscan/main";
    }
}
