package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final CourseService courseService;

    public Plan create(String title, long size, String memo) {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setCreatedDate(LocalDateTime.now());
        plan.setFirstCreator("user");
        plan.setSize(size);
        plan.setMemo(memo);
        planRepository.save(plan);
        for(long i = 1; i <= size; i++) {
            courseService.create(plan, i);
        }
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
        Pageable pageable = PageRequest.of(page, 8);
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