package com.ll.exam.damda.entity;

import com.ll.exam.damda.entity.design.map.Plan;
import com.ll.exam.damda.entity.user.SiteUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Plan plan;

    @ManyToOne
    private SiteUser siteUser;

}
