package com.ll.exam.damda.dto.search.review;

import com.ll.exam.damda.entity.search.Review;
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
    private LocalDateTime firstCreatedData;
    private LocalDateTime lastModifiedDate;
    private List<String> tags;
}
