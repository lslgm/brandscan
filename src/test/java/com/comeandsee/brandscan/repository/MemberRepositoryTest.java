package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.MemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("관리자 계정 생성 테스트")
    public void adminMemberCreateTest() {
        // Given
        MemberEntity admin = MemberEntity.builder()
                .email("brandscan@gmail.com")
                .name("관리자")
                .password(passwordEncoder.encode("admin1234"))
                .role(MemberRole.ADMIN)
                .build();

        // When
        MemberEntity result = repository.save(admin);

        // Then
        Assertions.assertEquals(result.getRole(), MemberRole.ADMIN);
    }
}
