package com.ll.exam.damda.repository.design.map;

import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.design.map.PlanSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanSpotRepository extends JpaRepository<PlanSpot, Long> {
    public List<PlanSpot> findAllByPlanId(long planId);

    public Optional<PlanSpot> findByPlanIdAndSpotId(long planId, long spotId);
}
