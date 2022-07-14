package com.eventcafecloud.cafe.repository;

import com.eventcafecloud.cafe.domain.Cafe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRepository extends JpaRepository<Cafe, Long>{

//    @Query("select c from Cafe c join fetch c.user u")
//    Page<Cafe> findAll(Pageable pageable);

    List<Cafe> findTop5ByOrderByCreatedDateDesc();
    Page<Cafe> findAllByCafeNameContaining(String searchVal, Pageable pageable);
    Page<Cafe> findAllByUserId(Long userId, Pageable pageRequest);
}
