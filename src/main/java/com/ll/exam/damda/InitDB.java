package com.ll.exam.damda;

import com.ll.exam.damda.entity.search.*;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import com.ll.exam.damda.repository.search.spot.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.testDBInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final SpotRepository spotRepository;
        private final ReviewRepository reviewRepository;

        public void testDBInit() {

            Random rand = new Random();

            /*여행지 생성*/
            for (int i = 0; i < 25; i++) {
                em.persist(Spot.builder()
                        .name("spot name" + i)
                        .city("제주")
                        .address("test address" + i)
                        .description("Test description" + i)
                        .build());
            }
            for (int i = 25; i < 50; i++) {
                em.persist(Spot.builder()
                        .name("spot name" + i)
                        .city("부산")
                        .address("test address" + i)
                        .description("Test description" + i)
                        .build());
            }
            for (int i = 50; i < 75; i++) {
                em.persist(Spot.builder()
                        .name("spot name" + i)
                        .city("강릉")
                        .address("test address" + i)
                        .description("Test description" + i)
                        .build());
            }
            for (int i = 75; i < 100; i++) {
                em.persist(Spot.builder()
                        .name("spot name" + i)
                        .city("전주")
                        .address("test address" + i)
                        .description("Test description" + i)
                        .build());
            }

            /*여행지 이미지 생성*/
            for (Spot spot : spotRepository.findAll()) {

                for (int i = 0; i < 3; i++) {
                    SpotImage spotImage = SpotImage.builder()
                            .URL("https://picsum.photos/400")
                            .spot(spot)
                            .build();
                    spot.getSpotImageURLs().add(spotImage);
                    em.persist(spotImage);
                }
            }

            /*여행지 리뷰 생성*/
            for (Spot spot : spotRepository.findAll()) {
                for (int i = 0; i < rand.nextInt(10); i++) {

                    Review review = Review.builder()
                            .title("good")
                            .content("so fun")
                            .start_date(LocalDateTime.of(2022, Month.APRIL, 1, 0, 0))
                            .end_date(LocalDateTime.of(2022, Month.APRIL, 2, 0, 0))
                            .build();
                    review.setSpot(spot);
                    em.persist(review);
                }
            }

            /*여행지 태그 생성*/
            String[] tagArr = new String[]{"연인끼리", "인스타", "가족끼리", "친구끼리", "액티비티", "볼거리"};
            List<Tag> tagList = new ArrayList<>();
            for (String stag : tagArr) {

                Tag tag = Tag.builder().name(stag).build();
                tagList.add(tag);
                em.persist(tag);
            }

            /*리뷰 & 태그 생성*/
            for (Review review : reviewRepository.findAll()) {
                int randIndex = rand.nextInt(5);

                List<ReviewTag> reviewTagList = new ArrayList<>();

                reviewTagList.add(ReviewTag.builder()
                        .review(review)
                        .tag(tagList.get(randIndex))
                        .build());
                reviewTagList.add(ReviewTag.builder()
                        .review(review)
                        .tag(tagList.get(randIndex+1))
                        .build());

                for (ReviewTag reviewTag : reviewTagList) {
                    em.persist(reviewTag);
                }
            }

        }
    }
}
