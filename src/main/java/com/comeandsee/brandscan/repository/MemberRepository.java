package com.comeandsee.brandscan.repository;

import com.comeandsee.brandscan.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {

    //이메일 중복 체크
    MemberEntity findByEmail(String email);
}
