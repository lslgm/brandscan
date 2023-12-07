package com.comeandsee.brandscan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_RETOUCH_TABLE_NAME;

@Getter
@Entity
@Table(name = BRAND_RETOUCH_TABLE_NAME)
public class BrandRetouchEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retouch_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToOne
    @JoinColumn(name = "reply_id")
    private BrandRetouchReplyEntity reply;
}
