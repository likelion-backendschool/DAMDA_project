package com.ll.exam.damda.controller.search.spot;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.service.search.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/spot")
public class SpotController {

    private final SpotService spotService;

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
}
