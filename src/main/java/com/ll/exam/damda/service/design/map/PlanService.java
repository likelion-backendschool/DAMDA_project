package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.user.UserPlan;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.PlanRepository;
import com.ll.exam.damda.repository.user.UserPlanRepository;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final UserService userService;
    private final UserPlanRepository userPlanRepository;
    private final BusketService busketService;
    private final PlanRepository planRepository;
    private final CourseService courseService;


    public Plan create(String title, LocalDate startDate, LocalDate endDate, String memo, String name) {
        Period period = Period.between(startDate, endDate);
        long size = (long)(period.getDays() + 1);
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setCreatedDate(LocalDateTime.now());
        plan.setFirstCreator(name);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setSize(size);
        plan.setMemo(memo);
        planRepository.save(plan);

        Busket busket = busketService.create(plan);
        plan.setBusket(busket);
        planRepository.save(plan);

        UserPlan userPlan = new UserPlan();
        userPlan.setPlan(plan);
        userPlan.setSiteUser(userService.getUser(name));
        userPlanRepository.save(userPlan);

        for(long i = 1; i <= size; i++) {
            courseService.create(plan, i);
        }
        planRepository.save(plan);
        return plan;
    }

    public Plan getPlan(long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if(optionalPlan.isPresent()) {
            return optionalPlan.get();
        } else {
            return null;
        }
    }

    public List<Plan> getAllPlan() {
        return planRepository.findAll();
    }

    public Page<Plan> getPlanList(int page, long siteUserId) {
        List<UserPlan> userPlans = userPlanRepository.findBySiteUserId(siteUserId);

        List<Plan> plans = userPlans.stream()
                .map(UserPlan::getPlan)
                .collect(Collectors.toList());

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), plans.size());
        Page<Plan> planPages = new PageImpl<>(plans.subList(start, end), pageable, plans.size());

        return planPages;
    }

    public void delete(Plan plan) {
        planRepository.delete(plan);
    }

    public void modifyBasic(long planId, String title, long size, String memo) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if(optionalPlan.isPresent()) {
            Plan plan = optionalPlan.get();
            plan.setTitle(title);
            plan.setSize(size);
            plan.setMemo(memo);
            planRepository.save(plan);
        } else {
            return;
        }
    }

    public UserPlan getUserPlan(long planId) {
        return userPlanRepository.findByPlanId(planId);
    }

    public void invite(UserPlan userPlan, String tempLink) {
        userPlan.setLink(tempLink);
        userPlan.setCreateDate(LocalDateTime.now());
        userPlanRepository.save(userPlan);
    }
}