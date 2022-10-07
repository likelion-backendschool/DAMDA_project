package com.ll.exam.damda.entity.search;

import com.ll.exam.damda.dto.search.spot.SpotImageDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpotImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_image_id")
    private Long id;

    @Column(name = "spot_image_url", nullable = false)
    private String url = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    public SpotImageDto toDto(SpotImage spotImage) {
        SpotImageDto spotImageDto = SpotImageDto.builder()
                .id(spotImage.getId())
                .url(spotImage.getUrl())
                .build();
        return spotImageDto;
    }
}