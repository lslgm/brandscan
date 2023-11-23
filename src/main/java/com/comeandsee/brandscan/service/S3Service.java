package com.comeandsee.brandscan.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.comeandsee.brandscan.constants.ManagementConstant.NOT_FOUND_PARAMETER_ERROR_MESSAGE;

@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    // Bucket 에 파일 업로드
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            String saveFileName = UUID.randomUUID() + "." + extension;

            // Fix me : 브랜드 요청 이미지만 처리
            amazonS3.putObject(bucket + "/request", saveFileName, multipartFile.getInputStream(), metadata);
            return amazonS3.getUrl(bucket + "/request", saveFileName).toString();

        } else {
            throw new NoSuchElementException(NOT_FOUND_PARAMETER_ERROR_MESSAGE + " : imgFile");
        }
    }
}
