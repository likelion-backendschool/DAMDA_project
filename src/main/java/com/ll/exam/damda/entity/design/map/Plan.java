package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.user.UserPlan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long size;

    @CreatedDate
    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private String firstCreator;

    private String lastModifier;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Course> courseList;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Busket busket;

    @OneToMany(mappedBy = "plan")
    private Set<UserPlan> userPlanSet;

//    @ManyToOne
//    private SiteUser siteUser;

//    @Column
//    private List<SpotDto> spotBusket;

//    @ManyToOne
//    private Member member;

}