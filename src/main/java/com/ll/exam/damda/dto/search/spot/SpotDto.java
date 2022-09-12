package com.ll.exam.damda.dto.search.spot;

import com.ll.exam.damda.entity.search.Review;
import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.search.SpotImage;
import com.ll.exam.damda.entity.search.Tag;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private List<Tag> mostTags;
    private Set<Review> reviews;
    private Set<SpotImage> spotImages;
    private int tagCnt;
    private long urlId;
    private String x;
    private String y;

    /* Page<Entity> -> Page<Dto> 변환처리 */
    public Page<SpotDto> toDtoList(Page<Spot> spots) {
        Page<SpotDto> spotDtos = spots.map(spot ->
                SpotDto.builder()
                        .id(spot.getId())
                        .name(spot.getName())
                        .city(spot.getCity())
                        .address(spot.getAddress())
                        .description(spot.getDescription())
                        .tagInfo(spot.getTagMap())
                        .mostTags(spot.getMostTagList())
                        .reviews(spot.getReviews())
                        .spotImages(spot.getSpotImageURLs())
                        .tagCnt(0)
                        .build()
        );
        return spotDtos;
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
                .mostTags(spot.getMostTagList())
                .reviews(spot.getReviews())
                .spotImages(spot.getSpotImageURLs())
                .tagCnt(0)
                .urlId(spot.getUrlId())
                .x(spot.getX())
                .y(spot.getY())
                .build();
    }

}
