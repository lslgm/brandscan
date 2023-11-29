package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandRequestDTO;
import com.comeandsee.brandscan.dto.PageDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

@Log4j2
@SpringBootTest
public class BrandRequestServiceTest {
    @Autowired
    private BrandRequestService service;

    @Test
    @DisplayName(value = "브랜드 요청 전체 목록 조회 서비스 테스트")
    public void BrandRequestFindAllWithPageServiceTest() {
        // Given
        int pageNumber = 1;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by("id").descending()
        );

        // When
        PageDTO<BrandRequestDTO> results = service.findAllWithPage(pageRequest);

        // Then
        log.info(results.toString());
    }

    @Test
    @DisplayName(value = "브랜드 요청 목록 조회 by 로그인 사용자 서비스 테스트")
    public void BrandRequestFindAllByMemberPageServiceTest() throws Exception {
        // Given
        String email = "test1@gmail.com";
        int pageNumber = 1;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // When
        PageDTO<BrandRequestDTO> result = service.findAllByMember(email, pageRequest);

        // Then
        log.info(result.toString());
    }

    @Test
    @DisplayName(value = "브랜드 요청 정보 아이디로 조회 서비스 테스트")
    public void BrandRequestFindByIdServiceTest() {
        // Given
        Long id = 1L;

        // When
        BrandRequestDTO result = service.findById(id);

        // Then
        Assertions.assertEquals(result.getId(), id);
    }

    @Test
    @DisplayName(value = "브랜드 요청 정보 상태 업데이트 서비스 테스트")
    public void BrandRequestSaveStateByIdServiceTest() {
        // Given
        Long id = 1L;
        int stateCode = 3;      // 3 : 완료

        // When
        BrandRequestDTO result = service.saveStateById(id, stateCode);

        // Then
        Assertions.assertEquals(result.getState().getCode(), stateCode);
    }

    @Test
    @DisplayName(value = "브랜드 등록 요청 서비스 테스트")
    public void BrandRequestRegisterServiceTest() throws Exception {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "beanpole.jpg",
                "image/jpg",
                new FileInputStream("src/test/resources/images/beanpole.jpg")
        );

        BrandRequestDTO brandRequestDTO = new BrandRequestDTO();
        brandRequestDTO.setTitle("테스트 요청 1");
        brandRequestDTO.setContent("테스트 요청 설명 1");
        brandRequestDTO.setImageFile(file);

        String email = "test1@gmail.com";

        // When
        BrandRequestDTO result = service.register(brandRequestDTO, email);

        // Then
        Assertions.assertEquals(result.getState(), brandRequestDTO.getState());
    }
}
