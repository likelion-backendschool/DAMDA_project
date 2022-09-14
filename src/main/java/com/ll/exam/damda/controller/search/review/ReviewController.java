package com.ll.exam.damda.controller.search.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final com.ll.exam.damda.repository.search.review.ReviewRepository ReviewRepository;

    @RequestMapping("/review")
    public String createReview() {
        return "review/createReview";
    }

    @RequestMapping("/review/show")
    public String readReview() {
        return "review/readReview";
    }

    /*
    @GetMapping("/review/show/{id}")

    public String detail(Model model, @PathVariable int id) {
        Review review = ReviewService.getReview(id);

        model.addAttribute("review", review);

        return "review/readReview";
    }

    */
}
