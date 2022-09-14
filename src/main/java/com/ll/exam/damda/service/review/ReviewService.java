package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;



    public void create(String title, String content) {
        Review review = new Review();
        review.setTitle(title);
        review.setContent(content);
        reviewRepository.save(review);
    }
}
