package com.comeandsee.brandscan.enums;

import lombok.Getter;

import java.util.Arrays;

import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_TYPE_ERROR_MESSAGE;

@Getter
public enum BrandRequestState {
    REQUEST(1, "요청중"),
    REVIEW(2, "검토중"),
    COMPLETE(3, "완료");

    private final int code;
    private final String displayName;

    BrandRequestState(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public static BrandRequestState ofCode(int dbData) {
        return Arrays.stream(BrandRequestState.values())
                .filter(value -> value.getCode() == dbData)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_TYPE_ERROR_MESSAGE));
    }
}
