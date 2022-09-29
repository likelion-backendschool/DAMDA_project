package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.dto.DtoUtil;
import com.ll.exam.damda.dto.design.map.BusketDto;
import com.ll.exam.damda.dto.design.map.PlanDto;
import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.user.UserPlan;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.PlanRepository;
import com.ll.exam.damda.repository.user.UserPlanRepository;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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

    //새로운 플래너 생성
    public Plan create(String title, String startDateString, String endDateString, String memo, String name) {
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        Period period = Period.between(startDate, endDate);
        long size = (long) (period.getDays() + 1);
        Plan plan = new Plan();
        plan.setTitle(title);
        plan.setCreatedDate(LocalDateTime.now());
        plan.setFirstCreator(name);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setSize(size);
        plan.setMemo(memo);
        plan.setStartDateString(startDateString);
        plan.setEndDateString(endDateString);
        planRepository.save(plan);

        Busket busket = busketService.create(plan);
        plan.setBusket(busket);
        planRepository.save(plan);

        UserPlan userPlan = new UserPlan();
        userPlan.setPlan(plan);
        userPlan.setSiteUser(userService.getUser(name));
        userPlanRepository.save(userPlan);

        for (long i = 1; i <= size; i++) {
            courseService.create(plan, i);
        }
        planRepository.save(plan);
        return plan;
    }

    //planId로 plan을 찾아 반환
    public Plan getPlan(long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isPresent()) {
            return optionalPlan.get();
        } else {
            return null;
        }
    }

    //planList 반환
    public Page<PlanDto> getPlanList(int page, long siteUserId) {
        List<UserPlan> userPlans = userPlanRepository.findBySiteUserId(siteUserId);

        List<Plan> plans = userPlans.stream()
                .map(UserPlan::getPlan)
                .collect(Collectors.toList());

        // Convert Plan -> PlanDto
        List<PlanDto> planDtos = new ArrayList<>();
        for (Plan plan : plans) {
            PlanDto planDto = DtoUtil.toPlanDto(plan);
            BusketDto busketDto = DtoUtil.toBusketDto(plan.getBusket());

            List<Spot> spots = plan.getBusket().getSpotList();
            List<SpotDto> spotDtos = new ArrayList<>();
            for (Spot spot : spots) {
                spotDtos.add(DtoUtil.toSpotDto(spot));
            }

            planDto.setBusket(busketDto);
            busketDto.setPlan(planDto);
            busketDto.setSpotList(spotDtos);
            planDtos.add(planDto);
        }

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), planDtos.size());
        Page<PlanDto> planPages = new PageImpl<>(planDtos.subList(start, end), pageable, planDtos.size());

        return planPages;
    }

    //플래너 삭제
    public void delete(Plan plan) {
        planRepository.delete(plan);
    }

    //플래너 기본 정보(플래너 이름, 여행 기간, 메모) 수정
    public void modifyBasic(Plan plan, String title, String startDateString, String endDateString, String memo) {
        long originalSize = plan.getSize();

        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        Period period = Period.between(startDate, endDate);
        long size = (long)(period.getDays() + 1);

        plan.setTitle(title);
        plan.setStartDate(startDate);
        plan.setEndDate(endDate);
        plan.setSize(size);
        plan.setMemo(memo);
        plan.setStartDateString(startDateString);
        plan.setEndDateString(endDateString);
        planRepository.save(plan);

        List<Course> courseList = plan.getCourseList();
        if(originalSize < size) {
            for(long i = originalSize+1; i <= size; i++) {
                courseService.create(plan, i);
            }
            planRepository.save(plan);
            return;
        }
        if(originalSize > size) {
            for(long i = size+1; i <= originalSize; i++) {
                Course course = courseService.getCourse(plan, i, true);
                courseList.remove(course);
                courseService.deleteCourse(course);
            }
            planRepository.save(plan);
        }
}

    public UserPlan getUserPlan(long planId) {
        return userPlanRepository.findByPlanId(planId);
    }


    public UserPlan getUserPlan(long siteUserId, long planId) {
        return userPlanRepository.findBySiteUserIdAndPlanId(siteUserId, planId);
    }

    public void invite(UserPlan userPlan, String tempLink) {
        userPlan.setLink(tempLink);
        userPlan.setCreateDate(LocalDateTime.now());
        userPlanRepository.save(userPlan);
    }

    public void createUserPlan(String name, UserPlan userPlan1) {
        UserPlan userPlan = new UserPlan();
        userPlan.setPlan(userPlan1.getPlan());
        userPlan.setSiteUser(userService.getUser(name));
        userPlanRepository.save(userPlan);
    }
}