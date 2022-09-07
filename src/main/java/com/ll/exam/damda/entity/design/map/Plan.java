package com.ll.exam.damda.entity.design.map;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String title;

    @Column
    private Long size;

    @CreatedDate
    private LocalDateTime CreatedDate;

    private LocalDateTime lastModifiedDate;

    private String firstCreator;

    private String lastModifier;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Course> courseList;

//    @Column
//    private List<SpotDto> spotBusket;

//    @ManyToOne
//    private Member member;
}