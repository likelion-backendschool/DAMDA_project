package com.ll.exam.damda.dto.design.map;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class CourseDto {

    private Long Id;
    private Long orders;
    private LocalDate travelDate;
    private PlanDto plan;
    private List<SpotDto> spotList;
}
