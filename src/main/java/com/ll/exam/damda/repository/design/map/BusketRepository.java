package com.ll.exam.damda.repository.design.map;

import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusketRepository extends JpaRepository<Busket, Long> {
    Busket findByPlan(Plan plan);
}
