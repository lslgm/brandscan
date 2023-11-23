package com.comeandsee.brandscan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import static com.comeandsee.brandscan.constants.ManagementConstant.BRAND_TABLE_NAME;

@Getter
@Entity
@Table(name = BRAND_TABLE_NAME)
public class BrandEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id; //브랜드 id

    @Column(nullable = false,length = 50)
    private String name; //브랜드 명

    @Lob
    private String description; //브랜드 설명

    @Column(name = "establish_year")
    private int year;//설립연도

    @Column(name = "home_page")
    private String homepage; //홈페이지 링크

    @Column(name = "brand_address" )
    private String address; //회사 주소(테이블에 추가)

    @Column(nullable = false,name = "logo_path")
    private String logo; //로고

    @Column(name = "building_path" )
    private String building; //본사(테이블에 추가)

    public void update(String name, String description, int year, String homepage, String address, String logo, String building) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
        }

        if (description != null && !description.equals(this.description)) {
            this.description = description;
        }

        if (year != this.year) {
            this.year = year;
        }

        if (homepage != null && !homepage.equals(this.homepage)) {
            this.homepage = homepage;
        }

        if (address != null && !address.equals(this.address)) {
            this.address = address;
        }

        if (logo != null && !logo.equals(this.logo)) {
            this.logo = logo;
        }

        if (building != null && !building.equals(this.building)) {
            this.building = building;
        }
    }
}
