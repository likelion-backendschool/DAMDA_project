package com.ll.exam.damda.service.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.ReviewTag;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.search.Tag;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import com.ll.exam.damda.repository.search.review.ReviewTagRepository;
import com.ll.exam.damda.repository.search.review.TagRepository;
import com.ll.exam.damda.service.search.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final TagRepository tagRepository;
    private final SpotService spotService;
    private final ReviewTagService reviewTagService;

    public void saveReviewTag(Review review, List<String> tagList){
        for (String _tag : tagList){
            Tag tag = tagRepository.findByName(_tag);
            ReviewTag reviewTag = ReviewTag.builder()
                            .review(review)
                                    .tag(tag)
                                            .build();
            reviewTagRepository.save(reviewTag);
        }
    }

    public void deleteReviewTag(Review review){
        for (ReviewTag reviewTag : reviewTagService.getReviewTagListByReviewId(review)){
            reviewTagRepository.deleteById(reviewTagService.getReviewTagId(reviewTag));
        }
    }


    public Page<Review> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstCreatedDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return this.reviewRepository.findAll(pageable);

    }

    public Page<Review> getListBySpot(Long spotId, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstCreatedDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        String name = spotService.findById(spotId).getName();

        return reviewRepository.findBySpot_nameContains(name, pageable);

    }

    public Page<Review> getListByUser(String name, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("firstCreatedDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // 한 페이지에 10까지 가능

        return reviewRepository.findBySiteUser_usernameContains(name, pageable);

    }
    public Review getReview(long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("no review not found,".formatted(id)));
    }

    public Review create(String title, String content, SiteUser siteUser, Spot spot) {

        Review review = new Review();
        review.setSpot(spot);
        review.setTitle(title);
        review.setContent(content);
        review.setSiteUser(siteUser);
        review.setFirstCreatedDate(LocalDateTime.now());

        reviewRepository.save(review);
        return review;
    }

    public void modify(Review review, String title, String content, List<String> reviewTag) {
        review.setTitle(title);
        review.setContent(content);
        review.setLastModifiedDate(LocalDateTime.now());

        reviewRepository.save(review);
    }

    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }



}
