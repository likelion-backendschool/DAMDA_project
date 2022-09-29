package com.ll.exam.damda.dto.search.review;

import com.ll.exam.damda.entity.search.ReviewTag;
import com.ll.exam.damda.entity.search.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewTagDto {
    private Long id;
    private Long reviewId;
    private Long tagId;
    private List<Tag> tagList;

    public ReviewDto reviewTagDto(ReviewTag reviewTag){
        return ReviewDto.builder()
                .id(reviewTag.getId())
                .build();
    }
}
