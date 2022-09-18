package com.ll.exam.damda.entity.design.map;

import com.ll.exam.damda.entity.search.Spot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
public class Busket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Spot> spotList;

    @OneToOne
    private Plan plan;
}
