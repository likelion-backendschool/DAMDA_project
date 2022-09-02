package com.ll.exam.damda.entity.search;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    @OneToMany(mappedBy = "review")
    private List<ReviewTag> reviewTag = new ArrayList<>();

    //==연관관계 메서드==//
    public void setSpot(Spot spot) {
        this.spot = spot;
        spot.setReviewCnt(spot.getReviewCnt() + 1);
    }

    //==조회 로직==//
    public Map<Tag, Integer> getTagInfo() {
        Map<Tag, Integer> tagInfo = new HashMap<>();

        for (ReviewTag _reviewTag : this.reviewTag) {
            Tag tag = _reviewTag.getTag();
            if (tagInfo.containsKey(tag)) {
                tagInfo.put(tag, tagInfo.get(tag) + 1);
            } else {
                tagInfo.put(tag, 1);
            }
        }

        return tagInfo;
    }

}