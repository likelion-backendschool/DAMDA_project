package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.UserPlan;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.PlanRepository;
import com.ll.exam.damda.repository.user.UserPlanRepository;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final UserService userService;
    private final UserPlanRepository userPlanRepository;
    private final BusketService busketService;
    private final PlanRepository planRepository;
    private final CourseService courseService;

    public Plan create(String title, long size, String memo, String name) {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setCreatedDate(LocalDateTime.now());
        plan.setFirstCreator(name);
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
    public Page<Plan> getPlanList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        return planRepository.findAll(pageable);
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
}