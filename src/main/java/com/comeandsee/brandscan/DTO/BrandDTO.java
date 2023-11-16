package com.comeandsee.brandscan.DTO;


import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BrandDTO {
    private Long id; //브랜드 id

    private String name; //브랜드 명

    private String description; //브랜드 설명

    private int year;//설립연도

    private String homepage; //홈페이지 링크

    private String address; //회사 주소

    private String logo; //로고

    private String building; //본사 건물

    private LocalDateTime created; //등록일

    private LocalDateTime updated; //수정일
}
