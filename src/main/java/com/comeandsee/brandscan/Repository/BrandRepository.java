package com.comeandsee.brandscan.Repository;

import com.comeandsee.brandscan.Entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    //임의 추가(스캔후 보내진 라벨 값(브랜드명)으로 찾기)
    BrandEntity findByName(String name);
}
