package com.comeandsee.brandscan.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Log4j2
@SpringBootTest
public class S3ServiceTest {
    @Autowired
    private S3Service s3Service;

    @Test
    @DisplayName("AWS S3 파일 업로드 서비스 테스트")
    public void uploadFileServiceTest() throws IOException {
        // Given
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "beanpole.jpg",
                "image/jpg",
                new FileInputStream("src/test/resources/images/beanpole.jpg")
        );

        // When
        String resultUrl = s3Service.uploadFile(file);

        // Then
        log.info(resultUrl);
    }
}
