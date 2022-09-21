package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    public Page<Review> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstCreatedDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return this.reviewRepository.findAll(pageable);

    }

    public Review getReview(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no review not found,".formatted(id)));
    }

    public void create(String title, String content) {
        Review review = new Review();
        review.setTitle(title);
        review.setContent(content);
        review.setFirstCreatedDate(LocalDateTime.now());
        reviewRepository.save(review);
    }

    public void modify(Review review, String title, String content) {
        review.setTitle(title);
        review.setContent(content);
        reviewRepository.save(review);
    }

    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }
}
