package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findBySiteUser_usernameContains(String name, Pageable pageable);

    Page<Review> findBySpot_nameContains(String name, Pageable pageable);
}
