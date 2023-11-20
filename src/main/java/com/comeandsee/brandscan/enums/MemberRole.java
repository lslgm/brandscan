package com.comeandsee.brandscan.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberRole {
    USER(1, "일반회원"),
    CUSTOMER(2, "고객사"),
    ADMIN(3, "관리자");

    private final int code;
    private final String displayName;

    MemberRole(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
    public static MemberRole ofCode(int dbData) {
        return Arrays.stream(MemberRole.values())
                .filter(value -> value.getCode() == dbData)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 회원 유형입니다."));
    }
}
