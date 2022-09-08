package com.ll.exam.damda.controller.design.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.service.design.map.BusketService;
import com.ll.exam.damda.service.design.map.CourseService;
import com.ll.exam.damda.service.design.map.PlanService;
import com.ll.exam.damda.service.design.map.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/travel/design")
public class PlanController {
    private final BusketService busketService;
    private final ObjectMapper objectMapper;
    private final PlanService planService;
    private final CourseService courseService;
    private final SpotService spotService;

    //플래너 리스트
    @GetMapping("/plan/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Plan> paging = planService.getPlanList(page);
        model.addAttribute("paging", paging);
        return "/design/map/plan_list";
    }
    //새로운 플래너
    @GetMapping("/new")
    public String createPlan() {
        return "/design/map/new_plan";
    }
    @PostMapping("/new")
    public String createPlan(@RequestParam(value = "title") String title,
                             @RequestParam(value = "size") long size,
                             @RequestParam(value = "memo") String memo) {
        Plan plan = planService.create(title, size, memo);
        return "redirect:/travel/design/modification/%d?order=%d".formatted(plan.getId(), 1);
    }

    //플래너 기본 정보 수정
    @GetMapping("/modification/basic/{planId}")
    public String modifyBasicPlan(Model model, @PathVariable long planId) {
        Plan plan = planService.getPlan(planId);
        model.addAttribute("plan", plan);
        return "/design/map/modify_basic";
    }
    @PostMapping("/modification/basic/{planId}")
    public String modifyBasicPlan(@PathVariable long planId,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam(value = "size") long size,
                                  @RequestParam(value = "memo") String memo) {
        Plan plan = planService.getPlan(planId);
        planService.modifyBasic(planId, title, size, memo);
        return "redirect:/travel/design/modification/%d?order=1".formatted(planId);
    }

    //플래너 수정
    @GetMapping("/modification/{planId}")
    public String modifyPlan(Model model, @PathVariable("planId") long planId, @RequestParam(value = "order") long order) {
        Plan plan = planService.getPlan(planId);
        Course course = courseService.getCourse(plan, order);
        model.addAttribute("plan", plan);
        model.addAttribute("course", course);
        return "/design/map/modify_plan";
    }

    //장바구니에 여행지 넣기
    @PostMapping("/insertSpot")
    @ResponseBody
    public String insertBusket(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "urlId") String urlId,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "planId") long planId) throws JsonProcessingException {
        System.out.println("insertBusket 수행");

        Spot spot = spotService.create(name, address, urlId, x, y);
        Plan plan = planService.getPlan(planId);

        boolean success = busketService.addSpotAtBusket(spot, plan);
        //장바구니에 추가
        if(success) {
            String spotJson = objectMapper.writeValueAsString(spot);
            System.out.println(spotJson);
            return spotJson;
        }
        return null;

    }
    @GetMapping("/getFinalSpot")
    @ResponseBody
    public String getFinalSpot() {
        return "spotJson";
    }
    //플래너 삭제
    @GetMapping("/plan/delete/{planId}")
    public String deletePlan(@PathVariable long planId) {
        Plan plan = planService.getPlan(planId);
        planService.delete(plan);
        return "redirect:/travel/design/plan/list";
    }
}