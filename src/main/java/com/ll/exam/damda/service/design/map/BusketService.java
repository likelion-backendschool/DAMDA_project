package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.repository.design.map.BusketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusketService {
    private final SpotService spotService;
    private final BusketRepository busketRepository;

    public Busket create(Plan plan) {
        Busket busket = new Busket();
        busket.setPlan(plan);
        busketRepository.save(busket);
        return busket;
    }

    public boolean addSpotAtBusket(Spot spot, Plan plan) {
        Busket busket = busketRepository.findByPlan(plan);
        List<Spot> spotList = busket.getSpotList();
        for(Spot spot2 : spotList) {
            if(spot.getUrlId().equals(spot2.getUrlId())) {
                spotService.delete(spot);
                return false;
            }
        }
        spotList.add(spot);

        busketRepository.save(busket);
        return true;
    }
}
