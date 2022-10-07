package com.ll.exam.damda.entity.design.map;


import com.ll.exam.damda.dto.design.map.CourseDto;
import com.ll.exam.damda.entity.search.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "course_order")
    private Long order;

    @CreatedDate
    @Column(name = "course_travel_date")
    private LocalDate travelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @ManyToMany
    @Builder.Default
    private Set<Spot> spots = new LinkedHashSet<>();

    public CourseDto toDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .Id(course.getId())
                .orders(course.getOrder())
                .travelDate(course.getTravelDate())
                .build();
        return courseDto;
    }
}