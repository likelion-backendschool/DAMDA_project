package com.ll.exam.damda.entity.user;

import javax.persistence.*;

import com.ll.exam.damda.entity.UserPlan;
import com.ll.exam.damda.entity.search.Review;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
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

    @OneToMany(mappedBy = "siteUser")
    private Set<UserPlan> userPlanSet;

    @OneToMany(mappedBy = "siteUser")
    private Set<Review> reviewSet;
}