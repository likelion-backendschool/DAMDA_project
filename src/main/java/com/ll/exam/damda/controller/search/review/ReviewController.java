package com.ll.exam.damda.controller.search.review;

import com.ll.exam.damda.dto.search.review.ReviewDto;
import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @RequestMapping("/review")
    public String createReview() {
        return "review/createReview";
    }

    @RequestMapping("/review/show")
    public String readReview() {
        return "review/readReview";
    }


    @RequestMapping("/review/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Review> paging = reviewService.getList(page);
        model.addAttribute("paging", paging);

        return "review/reviewList";
    }

    @GetMapping("/review/show/{id}")

    public String detail(Model model, @PathVariable long id, ReviewForm reviewForm) {
        Review review = reviewService.getReview(id);

        model.addAttribute("review", review);

        return "review/readReview";
    }


    @GetMapping("/review/create")
    public String questionCreate(ReviewForm reviewForm) {
        //빈 객체라도 생기도록 써준 것 reviewForm과 같이 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용이 가능하다.
        return "review/createReview";
    }

    @PostMapping("/review/create")
    public String reviewCreate(Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "review/createReview";
        }

        reviewService.create(reviewForm.getTitle(), reviewForm.getContent());

        return "redirect:list";
    }

}
