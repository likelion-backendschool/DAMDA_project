package com.ll.exam.damda.entity.search;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    //==생성 메서드==//
    public static List<ReviewTag> createReviewTag(Review review, Tag... tags) {
        List<ReviewTag> reviewTagList = new ArrayList<>();
        for (Tag tag : tags) {
            ReviewTag reviewTag = new ReviewTag();
            reviewTag.setReview(review);
            reviewTag.setTag(tag);
            reviewTagList.add(reviewTag);
        }
        return reviewTagList;
    }
}
