package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public Page<Review> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);

        return this.reviewRepository.findAll(pageable);

    }

    public Review getReview(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    public void create(String title, String content) {
        Review review = new Review();
        review.setTitle(title);
        review.setContent(content);
        review.setStart_date(LocalDateTime.now());
        reviewRepository.save(review);
    }
}
