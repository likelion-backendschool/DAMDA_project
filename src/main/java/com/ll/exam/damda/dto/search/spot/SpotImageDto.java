package com.ll.exam.damda.dto.search.spot;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SpotImageDto {
    private Long id;
    private String url = "";
    private SpotDto spot;
}
