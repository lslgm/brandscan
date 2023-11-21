package com.comeandsee.brandscan.converter;

import com.comeandsee.brandscan.enums.BrandRequestState;
import jakarta.persistence.AttributeConverter;

public class BrandRequestStateConverter implements AttributeConverter<BrandRequestState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BrandRequestState attribute) {
        return attribute.getCode();
    }

    @Override
    public BrandRequestState convertToEntityAttribute(Integer dbData) {
        return BrandRequestState.ofCode(dbData);
    }
}
