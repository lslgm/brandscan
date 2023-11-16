package com.comeandsee.brandscan.Repository;

import com.comeandsee.brandscan.Entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity,Long> {

}
