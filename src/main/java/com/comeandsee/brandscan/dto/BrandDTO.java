package com.comeandsee.brandscan.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_NAME_VALIDATION_MESSAGE;

@Getter
@Setter
@ToString
public class BrandDTO {
    private Long id; //브랜드 id

    @NotBlank(message = BRAND_NAME_VALIDATION_MESSAGE)
    private String name; //브랜드 명

    private String description; //브랜드 설명

    private int year;//설립연도

    private String homepage; //홈페이지 링크

    private String address; //회사 주소

    private String logo; //로고

    private String building; //본사 건물

    private LocalDateTime createdAt; //등록일

    private LocalDateTime updatedAt; //수정일
}
