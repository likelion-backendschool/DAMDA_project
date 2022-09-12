package com.ll.exam.damda.repository.search.spot;

import com.ll.exam.damda.entity.search.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {

    Page<Spot> findAll(Pageable pageable);

    Page<Spot> findAll(Specification<Spot> spec, Pageable pageable);

    List<Spot> findAll(Specification<Spot> spec);

}