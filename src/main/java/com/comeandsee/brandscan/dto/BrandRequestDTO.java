package com.comeandsee.brandscan.dto;

import com.comeandsee.brandscan.enums.BrandRequestState;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BrandRequestDTO {
    private Long id;
    @NotBlank(message = "제목은 필수로 입력하세요.")
    private String title;
    private String content;
    private String imagePath;
    private BrandRequestState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Custom
    private String stateToDisplay;
    private MultipartFile imageFile;

    public void setState(BrandRequestState state) {
        this.state = state;

        // Display name
        if (this.state != null) {
            this.stateToDisplay = this.state.getDisplayName();
        } else {
            this.stateToDisplay = "Unknown";
        }
    }
}
