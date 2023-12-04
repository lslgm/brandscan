package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.BrandRetouchEntity;
import com.comeandsee.brandscan.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRetouchRepository extends JpaRepository<BrandRetouchEntity,Long> {
    Page<BrandRetouchEntity> findAllByMember(MemberEntity member, Pageable pageable);
}
