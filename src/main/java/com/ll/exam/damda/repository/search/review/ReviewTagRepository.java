package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.ReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ReviewTagRepository extends JpaRepository<ReviewTag, Long>, RepositoryUtil {
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT = 1", nativeQuery = true)
    void truncate();
}
