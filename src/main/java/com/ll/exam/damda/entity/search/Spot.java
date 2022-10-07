package com.ll.exam.damda.entity.search;

import com.ll.exam.damda.dto.search.spot.SpotDto;
import com.ll.exam.damda.entity.design.map.PlanSpot;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    @Column(name = "spot_name")
    private String name;

    @Column(name = "spot_city")
    private String city;

    @Column(name = "spot_address")
    private String address;

    @Column(name = "spot_description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "spot_url_id")
    private long urlId;

    @Column(name = "spot_x")
    private String x;

    @Column(name = "spot_y")
    private String y;

    @Column(name = "spot_self_made_flag")
    private String selfMadeFlag;

    @Column(name = "spot_thumb_nail_img_path")
    private String thumbNailImgPath;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<SpotImage> spotImageURLs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<PlanSpot> planSpots = new LinkedHashSet<>();

    @Column(name = "spot_review_cnt", nullable = false)
    private int reviewCnt = 0;

    public SpotDto toSpotDto(Spot spot) {
        SpotDto spotDto = SpotDto.builder()
                .id(spot.getId())
                .name(spot.getName())
                .city(spot.getCity())
                .address(spot.getAddress())
                .description(spot.getDescription())
                .tagCnt(0)
                .urlId(spot.getUrlId())
                .x(spot.getX())
                .y(spot.getY())
                .thumbNailImgPath(spot.getThumbNailImgPath())
                .build();
        return spotDto;
    }

    //==조회 로직==//
    public Map<Tag, Integer> getTagMap() {
        Map<Tag, Integer> tagInfo = new HashMap<>();


        for (Review review : this.reviews) {
            for (Map.Entry<Tag, Integer> entry : review.getTagInfo().entrySet()) {
                if (!tagInfo.containsKey(entry.getKey())) {
                    tagInfo.put(entry.getKey(), entry.getValue());
                } else {
                    tagInfo.put(entry.getKey(), tagInfo.get(entry.getKey()) + 1);
                }
            }
        }

        return tagInfo;
    }

    public List<Tag> getMostTagList() {
        List<Tag> tagList = getTagMap().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Tag> mostTagList = new ArrayList<>();
        int i = 0;
        while (i < 3 && i < tagList.size()) {
            Tag tag = tagList.get(i);
            mostTagList.add(tag);
            i++;
        }

        return mostTagList;
    }
}