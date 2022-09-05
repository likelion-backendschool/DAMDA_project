package com.ll.exam.damda.entity.review;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    @NotNull
        private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String content;

    @OneToMany(mappedBy = "review")
    private List<ReviewTagEntity> reviewTag = new ArrayList<>();

    public void setSubject(String subject) {

    }
}
