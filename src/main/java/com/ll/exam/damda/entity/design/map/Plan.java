package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.user.UserPlan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
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

    private LocalDate startDate;

    private LocalDate endDate;

    private String startDateString;
    private String endDateString;

    @CreatedDate
    private LocalDateTime createdDate;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private List<Course> courseList;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private Set<UserPlan> userPlanSet;

    @OneToOne(mappedBy = "plan", cascade = CascadeType.REMOVE)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "plan")
    private Set<PlanSpot> planSpots;

//    @ManyToOne
//    private SiteUser siteUser;

//    @Column
//    private List<SpotDto> spotBusket;

//    @ManyToOne
//    private Member member;

}