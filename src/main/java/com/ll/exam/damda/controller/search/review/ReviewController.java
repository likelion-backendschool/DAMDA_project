package com.ll.exam.damda.controller.search.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


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

    /*
    @GetMapping("/review/show/{id}")

    public String detail(Model model, @PathVariable int id) {
        Review review = ReviewService.getReview(id);

        model.addAttribute("review", review);

        return "review/readReview";
    }

    */
    @GetMapping("/review/create")
    public String questionCreate(ReviewForm reviewForm) {
        //빈 객체라도 생기도록 써준 것 reviewForm과 같이 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용이 가능하다.
        return "review/createReview";
    }

    @PostMapping("/review/create")
    //controller가 model이라는 객체를 파라미터로 받게 하는 메서드
    public String reviewCreate(Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult) {
        //@Valid 사용시 ReviewForm의 룰을 하나씩 체크, 문제는 BindingResult안에 담긴다.
        // 위처럼 @Valid와 BindingResult는 순서를 바꾸지 않고 이어서 적어야 한다.

        if (bindingResult.hasErrors()) {
            //BindingError 안에 이미 에러에 대한 것이 구현되어 있다.
            return "review/createReview";
        }

        reviewService.create(reviewForm.getTitle(), reviewForm.getContent());
        return "redirect:/review/list"; // 리뷰 저장후 리뷰목록으로 이동
    }

}
