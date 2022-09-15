package com.ll.exam.damda.controller.search.spot;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.UserPlan;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.design.map.BusketRepository;
import com.ll.exam.damda.service.design.map.PlanService;
import com.ll.exam.damda.service.search.spot.SpotService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping("/spot")
public class SpotController {

    private final SpotService spotService;
    private final PlanService planService;
    private final UserService userService;

    @GetMapping("/detail")
    public String showSpotDetail(@RequestParam("spot") Long spotId, Model model) {
        model.addAttribute("spotDto", spotService.findById(spotId));
        return "spot/spotDetail";
    }

    @GetMapping("/show")
    public String getAllSpotBy(@RequestParam(value = "searchWord", defaultValue = "") String searchWord,
                               @RequestParam(value = "checkedValue", defaultValue = "") List<String> checkedValue,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Model model) {
        Page<SpotDto> spotDtoList = spotService.getSpotListBy(searchWord, checkedValue, page);
        model.addAttribute("checkedValue", checkedValue);
        model.addAttribute("spotDtoList", spotDtoList);
        return "spot/spotList";
    }

    @GetMapping("/addBusket")
    public String addSpotAtBusket(@RequestParam(value = "spotId") Long spotId, Model model, Principal principal) {
        SiteUser siteUser = userService.getUser(principal.getName());
        Set<UserPlan> userPlans = siteUser.getUserPlanSet();
        model.addAttribute("spotDto", spotService.findById(spotId));
        model.addAttribute("userPlans", userPlans);
        return "spot/addSpotAtBusket.html";
    }
}
