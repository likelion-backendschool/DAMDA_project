package com.ll.exam.damda.controller.search.review;

import com.ll.exam.damda.dto.search.review.ReviewDto;
import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.service.review.DataNotFoundException;
import com.ll.exam.damda.service.review.ReviewService;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @RequestMapping("/review")
    public String createReview() {
        return "review/createReview";
    }

    @RequestMapping("/review/show")
    public String readReview() {
        return "review/readReview";
    }

    /*@PreAuthorize("isAuthenticated()")*/
    @GetMapping("/review/modify/{id}")
    public String reviewModify(ReviewForm reviewForm, @PathVariable("id") long id) {
        //, Principal principal
        Review review = this.reviewService.getReview(id);

        if ( review == null ) {
            throw new DataNotFoundException("해당 질문은 존재하지 않습니다.");
        }

        /*
        if(!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        */
        reviewForm.setTitle(review.getTitle());
        reviewForm.setContent(review.getContent());

        return "review/createReview";
    }

    @PostMapping("/review/modify/{id}")
    public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult, @PathVariable("id") long id) {
        //Principal principal,
        if (bindingResult.hasErrors()) {
            return "review/createReview";
        }
        Review review = this.reviewService.getReview(id);

        if (review == null) {
            throw new DataNotFoundException("해당 리뷰는 존재하지 않습니다.");
        }


        /*
        if (!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        */
        reviewService.modify(review, reviewForm.getTitle(), reviewForm.getContent());

        return String.format("redirect:/review/show/%s", id);
    }
    @RequestMapping("/review/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Review> paging = reviewService.getList(page);
        model.addAttribute("paging", paging);

        return "review/reviewList";
    }

    @RequestMapping("/review/myList")
    public String myList(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<Review> paging = reviewService.getList(page);
        model.addAttribute("paging", paging);

        return "review/myReviewList";
    }
/*
    @RequestMapping("/review/list")
    public String list(Model model, int user_id, @RequestParam(defaultValue = "0") int page) {
        Page<Review> paging = reviewService.getListByUser(user_id, page);
        model.addAttribute("paging", paging);

        return "review/reviewList";
    }
*/
    @GetMapping("/review/show/{id}")
    public String detail(Model model, @PathVariable long id, ReviewForm reviewForm) {
        Review review = reviewService.getReview(id);

        model.addAttribute("review", review);

        return "review/readReview";
    }


    @GetMapping("/review/create")
    public String reviewCreate(ReviewForm reviewForm) {
        //빈 객체라도 생기도록 써준 것 reviewForm과 같이 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용이 가능하다.
        return "review/createReview";
    }

    @PostMapping("/review/create")
    public String reviewCreate(Principal principal,Model model, @Valid ReviewForm reviewForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "review/createReview";
        }

        SiteUser siteUser = userService.getUser(principal.getName());
        reviewService.create(reviewForm.getTitle(), reviewForm.getContent(), siteUser);

        return "redirect:list";
    }

    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/review/delete/{id}")
    public String reviewDelete(Principal principal, @PathVariable("id") Integer id) {
        Review review = reviewService.getReview(id);

        if (review == null) {
            throw new DataNotFoundException("%d번 질문은 존재하지 않습니다.");
        }

        /*if (!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }*/

        reviewService.delete(review);

        return "redirect:/review/list";
    }


    @RequestMapping("/requestSpot")
    public String requestSpot() {
        return "/requestSpot/requestSpot";
    }

    @RequestMapping("/managing")
    public String managing() {
        return "/managing/managing";
    }

}