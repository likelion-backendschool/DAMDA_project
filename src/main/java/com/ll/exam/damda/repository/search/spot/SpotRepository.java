package com.ll.exam.damda.repository.search.spot;

import com.ll.exam.damda.entity.search.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {


    Page<Spot> findAll(Specification<Spot> spec, Pageable pageable);

    List<Spot> findAll(Specification<Spot> spec);


    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    @Query("select s from Spot s")
    Page<Spot> findAllEntityGraph(Pageable pageable);

    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    @Query("select s from Spot s")
    List<Spot> findAllEntityGraph();
}
