package com.ll.exam.damda.entity.user;

import javax.persistence.*;

import com.ll.exam.damda.entity.UserPlan;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    private String password;

    @Column(unique = true)
    private String email;

    private String method;

    private LocalDateTime createDate;

    @OneToMany
    private Set<UserPlan> userPlanSet;
}