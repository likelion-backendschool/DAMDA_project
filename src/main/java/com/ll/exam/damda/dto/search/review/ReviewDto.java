package com.ll.exam.damda.dto.search.review;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime FirstCreatedData;
    private LocalDateTime LastModifiedDate;
    private List<String> Tags;

    public ReviewDto toDto(Review review){
        return ReviewDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .title(review.getTitle())
                .FirstCreatedData(review.getFirstCreatedDate())
                .LastModifiedDate(review.getLastModifiedDate())
                .Tags(review.getTagList(review))
                .build();
    }
}
