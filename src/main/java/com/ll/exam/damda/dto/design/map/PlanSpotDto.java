package com.ll.exam.damda.dto.design.map;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlanSpotDto {

    private long id;
    private PlanDto planDto;
    private SpotDto spotDto;
}
