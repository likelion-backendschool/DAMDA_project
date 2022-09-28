package com.ll.exam.damda.controller.design.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.exam.damda.config.user.DataNotFoundException;
import com.ll.exam.damda.dto.user.MessageDto;
import com.ll.exam.damda.dto.user.SiteUserContext;
import com.ll.exam.damda.entity.user.UserPlan;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.repository.user.UserPlanRepository;
import com.ll.exam.damda.repository.user.UserRepository;
import com.ll.exam.damda.service.design.chat.ChatService;
import com.ll.exam.damda.service.design.map.BusketService;
import com.ll.exam.damda.service.design.map.CourseService;
import com.ll.exam.damda.service.design.map.PlanService;
import com.ll.exam.damda.service.search.spot.SpotService;
import com.ll.exam.damda.service.user.UserSecurityService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.ll.exam.damda.util.Util.getRandomText;
import static com.ll.exam.damda.util.Util.showMessageAndRedirect;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping("/travel/design")
public class PlanController {
    private final UserPlanRepository userPlanRepository;
    private final BusketService busketService;
    private final ObjectMapper objectMapper;
    private final PlanService planService;
    private final CourseService courseService;
    private final SpotService spotService;
    private final ChatService chatService;
    private final UserService userService;

    //플래너 리스트
    @GetMapping("/plan/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @AuthenticationPrincipal SiteUserContext siteUserContext) {
        Page<Plan> paging = planService.getPlanList(page, siteUserContext.getId());
        model.addAttribute("paging", paging);
        return "design/map/plan_list";
    }

    //새로운 플래너
    @GetMapping("/new")
    public String createPlan() {
        return "design/map/new_plan";
    }

    @PostMapping("/new")
    public String createPlan(@RequestParam(value = "title") String title,
                             @RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
//                             @RequestParam(value = "size") long size,
                             @RequestParam(value = "memo") String memo,
                             Principal principal) {
        Plan plan = planService.create(title, startDate, endDate, memo, principal.getName());
        /* 플래너 생성시 채팅방 생성 */
        chatService.createRoom(plan);
        return "redirect:/travel/design/modification/%d?order=%d".formatted(plan.getId(), 1);
    }

    //플래너 기본 정보 수정
//    @GetMapping("/modification/basic/{planId}")
//    public String modifyBasicPlan(Model model, @PathVariable long planId) {
//        Plan plan = planService.getPlan(planId);
//        model.addAttribute("plan", plan);
//        return "/design/map/modify_basic";
//    }
//
//    @PostMapping("/modification/basic/{planId}")
//    public String modifyBasicPlan(@PathVariable long planId,
//                                  @RequestParam(value = "title") String title,
//                                  @RequestParam(value = "size") long size,
//                                  @RequestParam(value = "memo") String memo) {
//        Plan plan = planService.getPlan(planId);
//        planService.modifyBasic(planId, title, size, memo);
//        return "redirect:/travel/design/modification/%d?order=1".formatted(planId);
//    }

    //플래너 수정
    @GetMapping("/modification/{planId}")
    public String modifyPlan(Model model, @PathVariable("planId") long planId, @RequestParam(value = "order") long order) {
        Plan plan = planService.getPlan(planId);

        if(plan == null) {
            return "redirect:/travel/design/plan/list";
        }
        Course course = courseService.getCourse(plan, order);
        ChatRoomDto chatRoomDto = chatService.findByPlan_id(plan);
        Busket busket = busketService.getBusket(plan);
        model.addAttribute("plan", plan);
        model.addAttribute("course", course);
        model.addAttribute("spotList", busket.getSpotList());
        model.addAttribute("room", chatRoomDto);

        return "design/map/modify_plan";
    }

    //장바구니에 여행지 넣기
    @PostMapping("/insertSpot")
    @ResponseBody
    public Spot insertBusket(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "urlId") long urlId,
            @RequestParam(value = "x") String x,
            @RequestParam(value = "y") String y,
            @RequestParam(value = "planId") long planId) throws JsonProcessingException {
        System.out.println("insertBusket 수행");
        Spot spot = spotService.getSpotByUrlId(urlId);
        if (spot == null) {
            spot = spotService.create(name, address, urlId, x, y);
        }
        Plan plan = planService.getPlan(planId);

        boolean success = busketService.addSpotAtBusket(spot, plan);
        //장바구니에 추가
        if (success) {
            return spot;
        }
        throw new DataNotFoundException("error");

    }

    //장바구니에 여행지 넣기2
    @PostMapping("/insertSpot2")
    public String insertBusket(
            @RequestParam(value = "spotId") long spotId,
            @RequestParam(value = "planId") long planId) throws JsonProcessingException {
        System.out.println("insertBusket 수행");

        Spot spot = spotService.getSpot(spotId);
        Plan plan = planService.getPlan(planId);

        //장바구니에 추가
        boolean success = busketService.addSpotAtBusket(spot, plan);

        if (success) {
            return "redirect:/travel/design/plan/list";
        } else {
            return "redirect:/travel/design/plan/list";
        }
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
        List<UserPlan> userPlanList = userPlanRepository.findALLByPlan(plan);
        for(UserPlan userPlan : userPlanList) {
            userPlanRepository.delete(userPlan);
        }
        planService.delete(plan);
        return "redirect:/travel/design/plan/list";
    }

    @GetMapping("/getBusket")
    @ResponseBody
    public String getFinalBusket(@RequestParam long planId) throws JsonProcessingException {
        Plan plan = planService.getPlan(planId);
        Busket busket = busketService.getBusket(plan);
        List<Spot> busketList = busket.getSpotList();
        String result = objectMapper.writeValueAsString(busketList.get(busketList.size() - 1));
        System.out.println(result);
        return result;
    }

    @GetMapping("/getAllBusket")
    @ResponseBody
    public List<Spot> getAllBusket(@RequestParam long planId) throws JsonProcessingException {
        Plan plan = planService.getPlan(planId);
        Busket busket = busketService.getBusket(plan);
        List<Spot> busketList = busket.getSpotList();
        return busketList;
    }

    @GetMapping("/removeSpot")
    @ResponseBody
    public String removeSpotAtBusket(@RequestParam long planId, @RequestParam long spotId) {
        Spot spot = spotService.getSpot(spotId);
        //Busket에서 삭제
        Plan plan = planService.getPlan(planId);
        Busket busket = plan.getBusket();
        busketService.removeSpotAtBusket(busket, spot);
//        busket.getSpotList().remove(spot);
        //spot삭제
//        spotService.delete(spot);
//        System.out.println("삭제는 됨");
//        if(spot.)
        return "success";
    }

    @GetMapping("/insertCourse")
    @ResponseBody
    public String insertCourse(@RequestParam long courseId, @RequestParam long spotId) {
        Course course = courseService.getCourseById(courseId);
        Spot spot = spotService.getSpot(spotId);
        courseService.addSpotAtCourse(course, spot);
        return "success";
    }

    @GetMapping("/getAllCourse")
    @ResponseBody
    public List<Spot> getAllCourse(@RequestParam long courseId) {
        Course course = courseService.getCourseById(courseId);
        List<Spot> spotList = new ArrayList<>();
        for (Spot spot : course.getSpotList()) {
            if (spot.getSelfMadeFlag().equals("Y")) {
                Spot copySpot = spot;
                copySpot.setReviews(new LinkedHashSet<>());
                copySpot.setBuskets(new LinkedHashSet<>());
                copySpot.setSpotImageURLs(new LinkedHashSet<>());
                spotList.add(copySpot);
            } else {
                spotList.add(spot);
            }
        }
        return spotList;
    }

    @GetMapping("/getFinalSpotAtCourse")
    @ResponseBody
    public Spot getFinalSpotAtCourse(@RequestParam long courseId) throws JsonProcessingException {
        Course course = courseService.getCourseById(courseId);
        List<Spot> spotList = course.getSpotList();
        Spot spot = spotList.get(spotList.size() - 1);
        return spot;
    }

    @GetMapping("/removeCourse")
    @ResponseBody
    public String removeSpotAtCourse(@RequestParam long planId,
                                     @RequestParam long courseId,
                                     @RequestParam long spotId) {
        Course course = courseService.getCourseById(courseId);
        Spot spot = spotService.getSpot(spotId);
        courseService.removeSpotAtCourse(course, spot);
        spotService.removeCloneSpot(spotId);
        return "success";
    }

    @GetMapping("/plan/detail/{planId}")
    public String planDetail(Model model, @PathVariable long planId, @RequestParam long order) throws JsonProcessingException {
        Plan plan = planService.getPlan(planId);

        if(plan == null) {
            return "redirect:/travel/design/plan/list";
        }
        Course course = courseService.getCourse(plan, order);
        List<Spot> spotList = course.getSpotList();
//        String spotsString = objectMapper.writeValueAsString(spotList);
        model.addAttribute("plan", plan);
        model.addAttribute("course", course);
        model.addAttribute("spotList", spotList);

        return "design/map/plan_detail";
    }

    @GetMapping("/share/{planId}")
    public String planShare(Model model, Principal principal, @PathVariable long planId) {
        String alert = "소유자만 공유 가능합니다";
        String redirectUri = "/travel/design/plan/list";

        UserPlan userPlan = planService.getUserPlan(userService.getUserId(userService.getUser(principal.getName())), planId);

        if (userPlan.getSiteUser().getUsername().equals(principal.getName())) {

            String tempLink = getRandomText(10);
            planService.invite(userPlan, tempLink);

            alert = "링크를 공유하세요 : " + "http://localhost:8080/travel/design/share/invite/" + tempLink;
        }

        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    @GetMapping("/share/invite/{link}")
    public String planInvite(Model model, Principal principal, @PathVariable String link) {
        String alert = "이미 추가되어 있거나 링크가 유효하지 않습니다";
        String redirectUri = "/travel/design/plan/list";


        if (userPlanRepository.findByLink(link) != null) {
            UserPlan userPlan = userPlanRepository.findByLink(link);
            if (userPlanRepository.findBySiteUserIdAndPlanId(userService.getUserId(userService.getUser(principal.getName())), userPlan.getPlan().getId()) == null) {
                alert = "추가완료";
                planService.createUserPlan(principal.getName(), userPlan);
            }
        }

        MessageDto message = new MessageDto(alert, redirectUri, RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }
}