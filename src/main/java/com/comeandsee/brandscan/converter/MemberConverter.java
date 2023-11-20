package com.comeandsee.brandscan.converter;

import com.comeandsee.brandscan.enums.MemberRole;
import jakarta.persistence.AttributeConverter;

public class MemberConverter implements AttributeConverter<MemberRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(MemberRole attribute) {
        return attribute.getCode();
    }

    @Override
    public MemberRole convertToEntityAttribute(Integer dbData) {
        return MemberRole.ofCode(dbData);
    }
}
