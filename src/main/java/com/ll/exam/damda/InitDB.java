package com.ll.exam.damda;

import com.ll.exam.damda.entity.search.*;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import com.ll.exam.damda.repository.search.spot.SpotRepository;
import com.ll.exam.damda.repository.user.UserRepository;
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

        public void createSpot(String name, String city, String address, String description, long urlId, String x, String y, String flag, String thumbNailImgPath) {
            Spot spot = Spot.builder()
                    .name(name)
                    .city(city)
                    .address(address)
                    .description(description)
                    .urlId(urlId)
                    .x(x)
                    .y(y)
                    .selfMadeFlag(flag)
                    .thumbNailImgPath(thumbNailImgPath)
                    .build();

            em.persist(spot);
        }

        public void testDBInit() {

            Random rand = new Random();

            /*여행지 & 여행지 이미지 생성*/
            createSpot("원앤온리", "제주", "제주특별자치도 서귀포시 안덕면 산방로 141", "아메리카노가 맛있는 카페!"
                    , 217787831, "126.319192490757", "33.2392223486155", "Y", "/images/thumbnail/원앤온리.png");

            createSpot("올래국수", "제주", "제주특별자치도 제주시 귀아랑길 24", "제주 국수 맛집!"
                    , 1387964178, "126.49726716501328", "33.49152862178827", "Y", "/images/thumbnail/올래국수.png");

            createSpot("연돈", "제주", "제주특별자치도 서귀포시 일주서로 968-10", "골목식당 돈까스 맛집!"
                    , 1890778114, "126.40715814631936", "33.258895288625645", "Y", "/images/thumbnail/연돈.png");

            /*여행지 리뷰 생성*/
            for (Spot spot : spotRepository.findAll()) {
                for (int i = 0; i < rand.nextInt(10); i++) {
                    Review review = Review.builder()
                            .title("good")
                            .content("so fun")
                            .firstCreatedDate(LocalDateTime.of(2022, Month.APRIL, 1, 0, 0))
                            .lastModifiedDate(LocalDateTime.of(2022, Month.APRIL, 2, 0, 0))
                            .build();
                    review.setSpot(spot);
                    em.persist(review);
                }
            }

            /*여행지 태그 생성*/
            String[] tagArr = new String[]{"연인끼리", "가족끼리", "친구끼리", "혼자", "액티비티", "볼거리", "휴양", "인스타", "봄", "여름", "가을", "겨울"};
            List<Tag> tagList = new ArrayList<>();
            for (String stag : tagArr) {
                Tag tag = Tag.builder().name(stag).build();
                tagList.add(tag);
                em.persist(tag);
            }

            /*리뷰 & 태그 생성*/
            for (Review review : reviewRepository.findAll()) {
                int randIndex = rand.nextInt(11);
                List<ReviewTag> reviewTagList = new ArrayList<>();

                reviewTagList.add(ReviewTag.builder()
                        .review(review)
                        .tag(tagList.get(randIndex))
                        .build());
                reviewTagList.add(ReviewTag.builder()
                        .review(review)
                        .tag(tagList.get(randIndex + 1))
                        .build());

                for (ReviewTag reviewTag : reviewTagList) {
                    em.persist(reviewTag);
                }
            }
        }
    }
}