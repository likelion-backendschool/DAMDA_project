package com.ll.exam.damda.repository.user;

import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.user.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    List<UserPlan> findBySiteUserId(long siteUserId);

    UserPlan findByPlanId(long planId);

    UserPlan findByPlan(Plan plan);

    UserPlan findByLink(String link);

    UserPlan findBySiteUserIdAndPlanId(long siteUserId, long planId);
}
