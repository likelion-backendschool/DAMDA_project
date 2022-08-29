package com.ll.exam.damda.dto.search.spot;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.search.SpotImage;
import com.ll.exam.damda.entity.search.Tag;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpotDto {
    private Long id;
    private String name;
    private String city;
    private String address;
    private String description;
    private Map<Tag, Integer> tagInfo;
    private List<Tag> mostTagList;
    private List<Review> reviewList;
    private List<SpotImage> spotImageList;
    private int tagCnt;

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public Page<SpotDto> toDtoList(Page<Spot> spotList) {
        Page<SpotDto> spotDtoList = spotList.map(m ->
                SpotDto.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .city(m.getCity())
                        .address(m.getAddress())
                        .description(m.getDescription())
                        .tagInfo(m.getTagMap())
                        .mostTagList(m.getMostTagList())
                        .reviewList(m.getReviewList())
                        .spotImageList(m.getSpotImageURLList())
                        .tagCnt(0)
                        .build()
        );
        return spotDtoList;
    }

    /* Entity -> Dto */
    public SpotDto toDto(Spot spot) {
        return SpotDto.builder()
                .id(spot.getId())
                .name(spot.getName())
                .city(spot.getCity())
                .address(spot.getAddress())
                .description(spot.getDescription())
                .tagInfo(spot.getTagMap())
                .mostTagList(spot.getMostTagList())
                .reviewList(spot.getReviewList())
                .spotImageList(spot.getSpotImageURLList())
                .tagCnt(0)
                .build();
    }
}
