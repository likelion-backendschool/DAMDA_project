package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long>, RepositoryUtil {

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate();// 이거 지우면 안됨, truncateTable 하면 자동으로 이게 실행됨
    Page<Review> findBySiteUser_usernameContains(String name, Pageable pageable);

    Page<Review> findBySpot_nameContains(String name, Pageable pageable);
}
