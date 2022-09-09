package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.search.Spot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Busket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany
    private List<Spot> spotList;

    @OneToOne
    private Plan plan;
}
