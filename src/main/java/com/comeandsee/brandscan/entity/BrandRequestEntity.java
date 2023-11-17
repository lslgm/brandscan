package com.comeandsee.brandscan.entity;

import com.comeandsee.brandscan.converter.BrandRequestConverter;
import com.comeandsee.brandscan.enums.BrandRequestState;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_REQUEST_TABLE_NAME;

@Entity
@Table(name = BRAND_REQUEST_TABLE_NAME)
@Getter
@NoArgsConstructor
public class BrandRequestEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_request_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false)
    private String imagePath;   // Info : 배포 후 S3 경로로 바뀌어야 함.

    @Convert(converter = BrandRequestConverter.class)
    private BrandRequestState state;

    @Builder
    public BrandRequestEntity(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.state = BrandRequestState.REQUEST;
    }

    public void update(BrandRequestState state) {
        this.state = state;
    }
}
