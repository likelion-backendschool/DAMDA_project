package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Course;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.repository.design.map.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotService {
    private final SpotRepository spotRepository;

    public Spot create(String name, String address, String urlId, String x, String y) {
        Spot spot = new Spot();
        spot.setName(name);
        spot.setAddress(address);
        spot.setUrlId(urlId);
        spot.setX(x);
        spot.setY(y);
//        spot.setCourse(course);
        spotRepository.save(spot);
        return spot;
    }
}
