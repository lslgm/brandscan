package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.BrandRetouchReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRetouchReplyRepository extends JpaRepository<BrandRetouchReplyEntity, Long> {
}
