package com.ll.exam.damda.controller.search.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final com.ll.exam.damda.repository.search.review.ReviewRepository ReviewRepository;

    @RequestMapping("/review")
    public String reviewCreate() {
        return "review/createReview";
    }




}
