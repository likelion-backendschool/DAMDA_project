package com.ll.exam.damda.entity.search;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SpotImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String URL;

    @ManyToOne
    @JoinColumn(name = "Spot_id")
    private Spot spot;

    //==생성 메서드==//
    public static SpotImage createSpotImage(String url, Spot spot) {
        SpotImage spotImage = new SpotImage();
        spotImage.setURL(url);
        spotImage.setSpot(spot);
        spot.getSpotImageURLList().add(spotImage);
        return spotImage;
    }
}
