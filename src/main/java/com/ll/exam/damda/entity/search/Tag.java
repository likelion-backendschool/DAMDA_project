package com.ll.exam.damda.entity.search;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @OneToMany(mappedBy = "tag")
    @Builder.Default
    private Set<ReviewTag> reviewTags = new LinkedHashSet<>();
}
