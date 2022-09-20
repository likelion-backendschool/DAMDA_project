package com.ll.exam.damda.controller.search.spot;

import com.ll.exam.damda.HttpUtil;
import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.dto.user.SiteUserContext;
import com.ll.exam.damda.entity.UserPlan;
import com.ll.exam.damda.repository.user.UserPlanRepository;
import com.ll.exam.damda.service.search.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
@RequestMapping("/spot")
public class SpotController {

    private final SpotService spotService;
    private final UserPlanRepository userPlanRepository;

    @GetMapping("/detail")
    public String showSpotDetail(@RequestParam("spot") Long spotId, Model model) {
        SpotDto spotDto = spotService.findById(spotId);
        List<String> spotImgUrls = HttpUtil.getSpotImgUrl(spotDto.getName());
        model.addAttribute("spotDto", spotDto);
        model.addAttribute("imgUrlList", spotImgUrls);
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
    public String addSpotAtBusket(@RequestParam(value = "spotId") Long spotId, Model model, @AuthenticationPrincipal SiteUserContext siteUserContext) {
        List<UserPlan> userPlans = userPlanRepository.findBySiteUserId(siteUserContext.getId());
        model.addAttribute("spotDto", spotService.findById(spotId));
        model.addAttribute("userPlans", userPlans);
        return "spot/addSpotAtBusket.html";
    }
}
