package com.comeandsee.brandscan.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "brand_tbl")
public class BrandEntity extends BaseEntity{
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; //브랜드 id

    @Column(nullable = false,length = 50)
    private String name; //브랜드 명

    private String description; //브랜드 설명

    @Column(name = "establish_year")
    private int year;//설립연도

    @Column(name = "home_page")
    private String homepage; //홈페이지 링크

    @Column(name = "brand_address" )
    private String address; //회사 주소(테이블에 추가)

    @Column(nullable = false,name = "logo_path")
    private  String logo; //로고

    @Column(name = "building_path" )
    private  String building; //본사(테이블에 추가)


}
