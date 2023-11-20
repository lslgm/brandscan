package com.comeandsee.brandscan.dto;

import com.comeandsee.brandscan.enums.MemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberDTO {
    private Long id; //회원 id
    @NotBlank(message = "* 이메일은 필수 입력입니다.")
    @Email(message = "* 이메일 형식이 아닙니다.")
    private String email; //회원 이메일
    @NotBlank(message = "* 이름은 필수 입력입니다.")
    private String name; //회원 이름

    private String password; //회원 암호
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "* 휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String tel; //회원 전화번호

    private MemberRole state; //회원 유형(일반회원,고객사,관리자)

    private LocalDateTime created; //등록일

    private LocalDateTime updated; //수정일

    //custom
    private String memberRole; //유저 유형

    public void setMember(MemberRole state) {
        this.state = state;

        // Display name
        if (this.state != null) {
            this.memberRole = this.state.getDisplayName();
        } else {
            this.memberRole = "Unknown";
        }
    }
}
