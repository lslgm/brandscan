package com.comeandsee.brandscan.converter;

import com.comeandsee.brandscan.enums.MemberRole;
import jakarta.persistence.AttributeConverter;

public class MemberRoleConverter implements AttributeConverter<MemberRole, String> {
    @Override
    public String convertToDatabaseColumn(MemberRole attribute) {
        return attribute.getRole();
    }

    @Override
    public MemberRole convertToEntityAttribute(String dbData) {
        return MemberRole.ofRole(dbData);
    }
}
