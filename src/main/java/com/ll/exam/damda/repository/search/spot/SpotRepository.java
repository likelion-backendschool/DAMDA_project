package com.ll.exam.damda.repository.search.spot;

import com.ll.exam.damda.entity.search.Spot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {

    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    Page<Spot> findAll(Specification<Spot> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    List<Spot> findAll(Specification<Spot> spec);


    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    @Query("select s from Spot s where s.selfMadeFlag = 'Y'")
    Page<Spot> findAllEntityGraph(Pageable pageable);

    @EntityGraph(attributePaths = {"reviews", "spotImageURLs"})
    @Query("select s from Spot s where s.selfMadeFlag = 'Y'")
    List<Spot> findAllEntityGraph();

    Optional<Spot> findByUrlId(long urlId);
}
