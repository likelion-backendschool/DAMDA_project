package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long> {

    List<ReviewTag> findByReview_idContains(long id);
}
