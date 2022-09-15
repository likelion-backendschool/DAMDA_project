package com.ll.exam.damda.repository.user;

import com.ll.exam.damda.entity.UserPlan;
import com.ll.exam.damda.entity.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    List<UserPlan> findBySiteUser(SiteUser siteUser);
}
