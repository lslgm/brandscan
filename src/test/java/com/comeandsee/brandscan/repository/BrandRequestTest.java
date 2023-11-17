package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.BrandRequestEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandRequestTest {
    @Autowired
    private BrandRequestRepository repository;

    @Test
    @DisplayName(value = "브랜드 요청 테스트")
    public void brandRequestTest() {
        // Given
        BrandRequestEntity entity = BrandRequestEntity.builder()
                .title("테스트 브랜드 요청 제목")
                .content("테스트 브랜드 요청 내용")
                .imagePath("http://test.com/image/1/test.png")
                .build();

        // When
        BrandRequestEntity result = repository.save(entity);

        // Then
        Assertions.assertEquals(result.getTitle(), entity.getTitle());
    }
}
