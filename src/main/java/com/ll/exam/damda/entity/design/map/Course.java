package com.ll.exam.damda.entity.design.map;


import com.ll.exam.damda.entity.search.Spot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private Long orders;

    @CreatedDate
    private LocalDate travelDate;

    @ManyToOne
    private Plan plan;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private List<Spot> spotList;

}