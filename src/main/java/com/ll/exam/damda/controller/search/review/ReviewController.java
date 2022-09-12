package com.ll.exam.damda.controller.search.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final com.ll.exam.damda.repository.search.review.ReviewRepository ReviewRepository;

    @GetMapping("/review/create")
    public String reviewCreate(){
        return "review_form_hs";
    }



}
