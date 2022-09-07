package com.ll.exam.damda.entity.search;

import com.ll.exam.damda.entity.design.map.Course;
import com.sun.istack.NotNull;
import lombok.*;
import org.aspectj.apache.bcel.generic.Tag;

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
    private Long id;

    @Column
    @NotNull
    private String name;

//    @Column
//    @NotNull
//    private String city;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private Course course;

    private String urlId;

    private String x;

    private String y;

//    @OneToMany(mappedBy = "spot")
//    @Builder.Default
//    private List<SpotImage> spotImageURLList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "spot")
//    private List<Review> reviewList = new ArrayList<>();
//
//    @Column(columnDefinition = "integer default 0")
//    private int reviewCnt;
//
//    //==조회 로직==//
//    public Map<Tag, Integer> getTagMap() {
//        Map<Tag, Integer> tagInfo = new HashMap<>();
//
//        for (Review review : this.reviewList) {
//            for (Map.Entry<Tag, Integer> entry : review.getTagInfo().entrySet()) {
//                if (!tagInfo.containsKey(entry.getKey())) {
//                    tagInfo.put(entry.getKey(), entry.getValue());
//                } else {
//                    tagInfo.put(entry.getKey(), tagInfo.get(entry.getKey()) + 1);
//                }
//            }
//        }
//
//        return tagInfo;
//    }
//
//    public List<Tag> getMostTagList() {
//        List<Tag> tagList = getTagMap().entrySet().stream()
//                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//
//        List<Tag> mostTagList = new ArrayList<>();
//        int i = 0;
//        while (i < 3 && i < tagList.size()) {
//            Tag tag = tagList.get(i);
//            mostTagList.add(tag);
//            i++;
//        }
//
//        return mostTagList;
//    }
}