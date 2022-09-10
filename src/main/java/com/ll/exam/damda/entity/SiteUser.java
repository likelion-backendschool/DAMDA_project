package com.ll.exam.damda.entity;

import com.ll.exam.damda.entity.design.map.Busket;
import com.ll.exam.damda.entity.design.map.Plan;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

//    @OneToOne
//    private Busket busket;

//    @OneToMany
//    private Plan plan;
}
