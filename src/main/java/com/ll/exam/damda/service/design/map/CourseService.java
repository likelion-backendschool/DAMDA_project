package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.repository.design.map.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public void create(Plan plan, long i) {
        Course course = new Course();
        course.setPlan(plan);
        course.setOrders(i);
        courseRepository.save(course);
    }

    public Course getCourse(Plan plan, long order) {
        List<Course> courseList = plan.getCourseList();
        for(Course course : courseList) {
            if(course.getOrders() == order) {
                return course;
            }
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
}
