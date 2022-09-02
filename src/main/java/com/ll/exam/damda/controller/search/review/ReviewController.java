package com.ll.exam.damda.controller.search.review;

import com.ll.exam.damda.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final com.ll.exam.damda.repository.review.ReviewRepository ReviewRepository;

    @GetMapping("/review/create")
    public String reviewCreate(){
        return "review_form";
    }

    @PostMapping("/create")
    public String questionCreate(String subject, String content) {
        ReviewService.create(subject, content);
        return "redirect:/review/list"; // 리뷰 저장후 리뷰목록으로 이동
    }

}
