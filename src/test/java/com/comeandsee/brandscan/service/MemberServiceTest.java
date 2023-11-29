package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.MemberDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Log4j2
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService service;

    @Test
    @DisplayName("역할에 따른 회원 조회 서비스 테스트")
    public void findByRoleServiceTest() {
        // Given
        String role = "admin";
        PageRequest pageRequest = PageRequest.of(
                1,
                10,
                Sort.by("id").descending()
        );

        // When
        PageDTO<MemberDTO> result = service.findByRoleWithPage(role, pageRequest);

        // Then
        log.info(result.toString());
    }
	
	@Test
    public void joinMember() throws Exception {
        MemberDTO memberDTO = MemberDTO.builder()
                .name("홍길동")
                .email("hgd@gmail.com")
                .password("user123!")
                .build();
        service.saveMember(memberDTO);
    }
}
