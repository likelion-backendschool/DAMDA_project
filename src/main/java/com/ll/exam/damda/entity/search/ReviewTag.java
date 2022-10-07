package com.ll.exam.damda.entity.search;


import com.ll.exam.damda.dto.search.review.ReviewTagDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public ReviewTagDto toDto(ReviewTag reviewTag) {
        ReviewTagDto reviewTagDto = ReviewTagDto.builder()
                .id(reviewTag.getId())
                .build();
        return reviewTagDto;
    }
}