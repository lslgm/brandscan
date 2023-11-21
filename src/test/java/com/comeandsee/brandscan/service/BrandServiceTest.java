package com.comeandsee.brandscan.service;

import com.comeandsee.brandscan.dto.BrandDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandServiceTest {
    @Autowired
    private BrandService service;

    @Test
    @DisplayName("브랜드 등록 서비스 테스트")
    public void brandRegisterServiceTest() {
        // Given
        BrandDTO brand = new BrandDTO();
        brand.setName("테스트 브랜드 이름");
        brand.setDescription("테스트 브랜드 설명");
        brand.setYear(2023);
        brand.setHomepage("http://www.google.co.kr");
        brand.setAddress("구글 본사");
        brand.setLogo("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1920px-Google_2015_logo.svg.png");
        brand.setBuilding("https://villiv.co.kr/data/2022/08/2022-08-09_12-35-41-41730-1660016141.jpg");

        // When
        BrandDTO result = service.register(brand);

        // Then
        Assertions.assertEquals(result.getName(), brand.getName());
    }

    @Test
    @DisplayName("브랜드 아이디로 단일 정보 조회 서비스 테스트")
    public void brandFindByIdServiceTest() {
        // Given
        Long id = 1L;

        // When
        BrandDTO result = service.findById(id);

        // Then
        Assertions.assertEquals(result.getId(), id);
    }

    @Test
    @DisplayName("브랜드 아이디로 정보 업데이트 서비스 테스트")
    public void brandUpdateByIdServiceTest() {
        // Given
        BrandDTO dto = new BrandDTO();
        dto.setId(1L);
        dto.setAddress("업데이트 구글 본사 주소");

        // When
        BrandDTO result = service.updateById(dto);

        // Then
        Assertions.assertEquals(result.getAddress(), dto.getAddress());
    }
}
