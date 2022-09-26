package com.ll.exam.damda.repository.search.review;

import com.ll.exam.damda.entity.search.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
