package com.ll.exam.damda.dto.search.review;

import com.ll.exam.damda.entity.search.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime start_data;
    private LocalDateTime end_date;

    public ReviewDto toDto(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .title(review.getTitle())
                .start_data(review.getStartDate())
                .end_date(review.getEndDate())
                .build();
    }
}
