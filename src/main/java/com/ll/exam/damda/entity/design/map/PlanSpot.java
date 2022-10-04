package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.search.Spot;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PlanSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    public void setPlanSpot(Plan plan, Spot spot) {
        PlanSpot planSpot = new PlanSpot();

        this.plan = plan;
        this.spot = spot;
    }
}
