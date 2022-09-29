package com.ll.exam.damda.dto.user;

import com.ll.exam.damda.dto.design.map.PlanDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UserPlanDto {

    private Long id;
    private String link;
    private LocalDateTime createDate;
    private PlanDto plan;
    private SiteUserContext siteUser;

}
