package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.BrandRequestEntity;
import com.comeandsee.brandscan.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRequestRepository extends JpaRepository<BrandRequestEntity, Long> {
    Page<BrandRequestEntity> findAllByMember(MemberEntity member, Pageable pageable);
}
