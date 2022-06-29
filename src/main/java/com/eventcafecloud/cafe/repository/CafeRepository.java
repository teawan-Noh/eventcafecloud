package com.eventcafecloud.cafe.repository;

import com.sparta.springcore.cafe.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long>{
}
