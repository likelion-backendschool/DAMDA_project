package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.dto.DtoUtil;
import com.ll.exam.damda.dto.design.map.CourseDto;
import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.repository.design.map.CourseRepository;
import com.ll.exam.damda.service.search.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final SpotService spotService;
    private final CourseRepository courseRepository;

    public void create(Plan plan, long i) {
        Course course = new Course();
        course.setPlan(plan);
        course.setOrders(i);
        courseRepository.save(course);
    }

    public CourseDto getCourse(Plan plan, long order) {
        List<Course> courseList = plan.getCourseList();
        for(Course course : courseList) {
            if(course.getOrders() == order) {
                CourseDto courseDto = DtoUtil.toCourseDto(course);

                List<SpotDto> spotDtoList = new ArrayList<>();
                for (Spot spot : course.getSpotList()) {
                    spotDtoList.add(DtoUtil.toSpotDto(spot));
                }

                courseDto.setSpotList(spotDtoList);

                return courseDto;
            }
        }
        return null;
    }

    public Course getCourse(Plan plan, long order, Boolean isEntity) {
        List<Course> courseList = plan.getCourseList();
        for (Course course : courseList) {
            if (course.getOrders() == order) return course;
        }
        return null;
    }

    public Course getCourseById(long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else {
            return null;
        }
    }

    public void addSpotAtCourse(Course course, Spot spot) {
        List<Spot> spotList = course.getSpotList();
//        Spot spot = spotService.getSpot(spot.getId());

//        Spot spotClone = spotService.cloneSpot(spot);
        spotList.add(spot);
        courseRepository.save(course);
    }

    public void removeSpotAtCourse(Course course, Spot spot) {
        List<Spot> spotList = course.getSpotList();
        spotList.remove(spot);
        courseRepository.save(course);
    }

    public void deleteCourse(Plan plan, long i) {
        CourseDto courseDto = getCourse(plan, i);
        courseRepository.deleteById(courseDto.getId());
    }

    public void deleteCourse(Course course) {
        courseRepository.delete(course);
    }
}
