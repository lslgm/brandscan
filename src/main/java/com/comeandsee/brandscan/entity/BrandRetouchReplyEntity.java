package com.comeandsee.brandscan.entity;

import jakarta.persistence.*;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_RETOUCH_REPLY_TABLE_NAME;

@Entity
@Table(name = BRAND_RETOUCH_REPLY_TABLE_NAME)
public class BrandRetouchReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    @Lob
    private String content;
}
