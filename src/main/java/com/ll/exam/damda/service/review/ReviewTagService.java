package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.ReviewTag;
import com.ll.exam.damda.repository.search.review.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewTagService {
    private final ReviewTagRepository reviewTagRepository;

    public List<ReviewTag> getReviewTagListByReviewId(Review review) {
        return reviewTagRepository.findAllByReview(review);
    }

    public long getReviewTagId(ReviewTag reviewTag) {
        return reviewTag.getId();
    }
}