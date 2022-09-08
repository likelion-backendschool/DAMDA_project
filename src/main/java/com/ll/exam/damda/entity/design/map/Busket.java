package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.search.Spot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@Entity
public class Busket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany
    private Set<Spot> spotList = new LinkedHashSet<>();

    @OneToOne
    private Plan plan;
}
