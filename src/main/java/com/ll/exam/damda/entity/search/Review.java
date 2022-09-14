package com.ll.exam.damda.entity.search;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

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

    @Column(name = "review_title", nullable = false)
    private String title;

    @Column(name = "review_content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "review_travel_start_date")
    private LocalDateTime start_date;

    @Column(name = "review_travel_end_date")
    private LocalDateTime end_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToMany(mappedBy = "review")
    @Builder.Default
    private Set<ReviewTag> reviewTags = new LinkedHashSet<>();

    //==연관관계 메서드==//
    public void setSpot(Spot spot) {
        this.spot = spot;
        spot.setReviewCnt(spot.getReviewCnt() + 1);
    }


    //==조회 로직==//
    public Map<Tag, Integer> getTagInfo() {
        Map<Tag, Integer> tagInfo = new HashMap<>();


        for (ReviewTag _reviewTag : this.reviewTags) {
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
