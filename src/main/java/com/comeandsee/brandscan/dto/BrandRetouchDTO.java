package com.comeandsee.brandscan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BrandRetouchDTO {
    private Long id;
    @NotBlank(message = "* 제목은 필수 입력입니다.")
    private String title;

    private String content;

    private LocalDateTime createdAt; //등록일

    private LocalDateTime updatedAt; //수정일
}
