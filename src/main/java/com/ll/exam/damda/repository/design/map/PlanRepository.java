package com.ll.exam.damda.repository.design.map;

import com.ll.exam.damda.entity.design.map.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
