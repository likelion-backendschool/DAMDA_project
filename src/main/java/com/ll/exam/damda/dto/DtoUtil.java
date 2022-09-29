package com.ll.exam.damda.dto;

import com.ll.exam.damda.dto.design.map.BusketDto;
import com.ll.exam.damda.dto.design.map.CourseDto;
import com.ll.exam.damda.dto.design.map.PlanDto;
import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;

/*연관 관계를 제외한 정보만 가지고 있는 dto 반환*/
public class DtoUtil {

    public static PlanDto toPlanDto(Plan plan) {
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

    public static BusketDto toBusketDto(Busket busket) {
        BusketDto busketDto = BusketDto.builder()
                .Id(busket.getId())
                .build();
        return busketDto;
    }

    public static SpotDto toSpotDto(Spot spot) {
        SpotDto spotDto = SpotDto.builder()
                .id(spot.getId())
                .name(spot.getName())
                .city(spot.getCity())
                .address(spot.getAddress())
                .description(spot.getDescription())
                .tagCnt(0)
                .urlId(spot.getUrlId())
                .x(spot.getX())
                .y(spot.getY())
                .thumbNailImgPath(spot.getThumbNailImgPath())
                .build();
        return spotDto;
    }

    public static CourseDto toCourseDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .Id(course.getId())
                .orders(course.getOrders())
                .travelDate(course.getTravelDate())
                .build();
        return courseDto;
    }
}
