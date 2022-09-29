package com.ll.exam.damda.dto.design.map;

import com.ll.exam.damda.dto.design.chat.ChatRoomDto;
import com.ll.exam.damda.entity.design.chat.ChatRoom;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.user.UserPlan;
import lombok.Builder;
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
@Builder
public class PlanDto {

    private Long id;
    private String title;
    private Long size;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String firstCreator;
    private String lastModifier;
    private String memo;
    private List<CourseDto> courseList;
    private BusketDto busket;
    private Set<UserPlan> userPlanSet;
    private ChatRoomDto chatRoom;

}
