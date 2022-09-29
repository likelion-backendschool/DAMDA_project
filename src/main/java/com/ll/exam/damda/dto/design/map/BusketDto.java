package com.ll.exam.damda.dto.design.map;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BusketDto {

    private Long Id;
    private List<SpotDto> spotList;
    private PlanDto plan;

}
