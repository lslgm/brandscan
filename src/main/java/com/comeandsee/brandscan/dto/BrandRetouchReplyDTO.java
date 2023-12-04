package com.comeandsee.brandscan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_RETOUCH_CONTENT_VALIDATION_MESSAGE;

@Getter
@Setter
@ToString
public class BrandRetouchReplyDTO {
    private Long id;

    @NotBlank(message = BRAND_RETOUCH_CONTENT_VALIDATION_MESSAGE)
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
