package com.ll.exam.damda;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test {
    @Autowired
    private ReviewRepository reviewRepository;

    /*@Test
    void testJpa() {
        Review review = new Review();
        review.setTitle("재미와 휴식");
        review.setContent("다시 또 가고싶어요!!");
        this.reviewRepository.save(review);
    }*/
}
