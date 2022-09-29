package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.ReviewTag;
import com.ll.exam.damda.repository.search.review.ReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewTagService {
    private final ReviewTagRepository reviewTagRepository;

    public List<ReviewTag> getReviewTagListByReview(Review review) {
        return reviewTagRepository.findAllByReview(review);
    }

    public List<String> getReviewTagList(List<ReviewTag> reviewTags){
        List<String> reviewTagList = new ArrayList<>();
        for(ReviewTag _reviewTag:reviewTags){
            reviewTagList.add(getTagNameByReviewTag(_reviewTag));
        }
        return reviewTagList;
    }

    public String getTagNameByReviewTag(ReviewTag reviewTag){
        return reviewTag.getTag().getName();
    }

    public long getReviewTagId(ReviewTag reviewTag) {
        return reviewTag.getId();
    }
}