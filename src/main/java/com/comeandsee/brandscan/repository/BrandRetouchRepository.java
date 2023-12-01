package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.BrandRetouchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRetouchRepository extends JpaRepository<BrandRetouchEntity,Long> {
}
