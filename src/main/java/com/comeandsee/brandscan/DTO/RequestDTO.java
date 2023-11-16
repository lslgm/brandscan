package com.comeandsee.brandscan.DTO;

import jakarta.validation.constraints.NotBlank;

import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RequestDTO {
    private Long id; //브랜드 요청 id

    @NotBlank(message = "제목은 필수로 입력하세요")
    private String title; //브렌드 요청 제목

    private String content; //브렌드 요청 내용
    /*@NotNull(message = "파일은 필수로 입력하세요")
    private MultipartFile image; //브랜드 요청 첨부파일*/

    private  String imgPath;

    private String status; //브렌드 요청 처리 상태

    private LocalDateTime created; //등록일

    private LocalDateTime updated; //수정일
}
