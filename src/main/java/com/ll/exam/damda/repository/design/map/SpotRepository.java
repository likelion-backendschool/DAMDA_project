package com.ll.exam.damda.repository.design.map;

import com.ll.exam.damda.entity.search.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
}
