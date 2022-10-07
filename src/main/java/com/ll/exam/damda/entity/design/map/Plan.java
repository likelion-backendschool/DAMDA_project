package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.dto.design.map.PlanDto;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.user.UserPlan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_title")
    private String title;

    @Column(name = "plan_size")
    private Long size;

    @Column(name = "plan_start_date")
    private LocalDate startDate;

    @Column(name = "plan_end_date")
    private LocalDate endDate;

    @Column(name = "plan_start_date_string")
    private String startDateString;

    @Column(name = "plan_end_date_string")
    private String endDateString;

    @CreatedDate
    @Column(name = "plan_created_date")
    private LocalDateTime createdDate;

    @Column(name = "plan_last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "plan_first_creator")
    private String firstCreator;

    @Column(name = "plan_last_modifier")
    private String lastModifier;

    @Column(name = "plan_memo", columnDefinition = "TEXT")
    private String memo;

    @OneToOne(mappedBy = "plan", cascade = CascadeType.ALL)
    private ChatRoom chatRoom;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private Set<Course> courses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private Set<UserPlan> userPlans = new LinkedHashSet<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private Set<PlanSpot> planSpots = new LinkedHashSet<>();

    public PlanDto toDto(Plan plan) {
        PlanDto planDto = PlanDto.builder()
                .id(plan.getId())
                .title(plan.getTitle())
                .size(plan.getSize())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .createdDate(plan.getCreatedDate())
                .lastModifiedDate(plan.getLastModifiedDate())
                .memo(plan.getMemo())
                .build();
        return planDto;
    }
}