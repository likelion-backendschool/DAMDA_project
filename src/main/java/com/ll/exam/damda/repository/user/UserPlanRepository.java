package com.ll.exam.damda.repository.user;

import com.ll.exam.damda.entity.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
