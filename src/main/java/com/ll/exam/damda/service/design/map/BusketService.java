package com.ll.exam.damda.service.design.map;

import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.repository.design.map.BusketRepository;
import com.ll.exam.damda.service.search.spot.SpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusketService {
    private final SpotService spotService;
    private final BusketRepository busketRepository;

    public Busket create(Plan plan) {
        Busket busket = new Busket();
        busket.setPlan(plan);
        busket.setSpotList(new ArrayList<>());
        busketRepository.save(busket);
        return busket;
    }

    public boolean addSpotAtBusket(Spot spot, Plan plan) {
        Busket busket = busketRepository.findByPlan(plan);
        List<Spot> spotList = busket.getSpotList();
        for(Spot spot2 : spotList) {
            if(spot.getUrlId() == spot2.getUrlId()) {
                return false;
            }
        }
        spotList.add(spot);

        busketRepository.save(busket);
        return true;
    }

    public Busket getBusket(Plan plan) {
        return busketRepository.findByPlan(plan);
    }

    public void removeSpotAtBusket(Busket busket, Spot spot) {
        busket.getSpotList().remove(spot);
        busketRepository.save(busket);
    }
}
