package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final CourseService courseService;

    public Plan create(String title, long size) {
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setFirstCreatedDate(LocalDateTime.now());
        plan.setFirstCreator("user");
        plan.setSize(size);
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
}