package com.comeandsee.brandscan.enums;

import lombok.Getter;

import java.util.Arrays;

import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_TYPE_ERROR_MESSAGE;

@Getter
public enum MemberRole {
    USER("일반회원", "ROLE_USER"),
    CUSTOMER("고객사", "ROLE_CUSTOMER"),
    ADMIN("관리자", "ROLE_ADMIN");

    private final String displayName;
    private final String role;

    MemberRole(String displayName, String role) {
        this.displayName = displayName;
        this.role = role;
    }
    public static MemberRole ofRole(String dbData) {
        return Arrays.stream(MemberRole.values())
                .filter(value -> value.getRole().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_TYPE_ERROR_MESSAGE));
    }
}
