package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.MemberEntity;
import com.comeandsee.brandscan.enums.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    MemberEntity findByEmail(String email); //이메일 중복 체크
    Page<MemberEntity> findByRole(MemberRole role, Pageable pageable);  // 역할에 따른 정보 조회
}
