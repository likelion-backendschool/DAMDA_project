package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //Page<Review> findByUser(long user_id, Pageable pageable);
}
