package com.comeandsee.brandscan.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@Table(name = "brand_request_tbl")
@ToString
public class RequestEntity extends BaseEntity{
    @Id
    @Column(name = "brand_request_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; //브랜드 요청 id

    @Column(nullable = false,length = 100)
    private String title; //브렌드 요청 제목

    private String content;  //브렌드 요청 내용
    @Column(name = "image_path")
    private String imgPath; //브랜드 요청 파일


    private String status; //브렌드 요청 처리 상태

    //임시 조인
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberid")
    private MemberEntity memberEntity;*/
}
