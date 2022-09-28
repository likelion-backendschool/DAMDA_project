package com.ll.exam.damda;

import com.ll.exam.damda.entity.search.*;
import com.ll.exam.damda.entity.user.SiteUser;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import com.ll.exam.damda.repository.search.spot.SpotRepository;
import com.ll.exam.damda.repository.user.UserRepository;
import com.ll.exam.damda.service.user.UserService;
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
        private final UserService userService;

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
            createSpot("원앤온리", "제주", "제주특별자치도 서귀포시 안덕면 산방로 141", "제주에서 가장 길고 고요한 황우치해변을 홀로 품고 명승 산방산을 지붕삼은 유일한 곳"
                    , 217787831, "126.319192490757", "33.2392223486155", "Y", "/images/thumbnail/원앤온리.png");

            createSpot("올래국수", "제주", "제주특별자치도 제주시 귀아랑길 24", "제주에서만 맛볼 수 있는 국수!"
                    , 1387964178, "126.49726716501328", "33.49152862178827", "Y", "/images/thumbnail/올래국수.png");

            createSpot("연돈", "제주", "제주특별자치도 서귀포시 일주서로 968-10", "골목식당에 나온 돈까스 맛집!"
                    , 1890778114, "126.40715814631936", "33.258895288625645", "Y", "/images/thumbnail/연돈.png");

            createSpot("숙성도 노형점", "제주", "제주특별자치도 제주시 원노형로 41", "숙성 돼지고기 맛집! 줄서서 먹는 맛집!"
                    , 316010726, "126.48502796132595", "33.48503198254993", "Y", "/images/thumbnail/숙성도 노형점.png");

            /*여행지 태그 생성*/
            String[] tagArr = new String[]{"연인끼리", "가족끼리", "친구끼리", "혼자", "액티비티", "볼거리", "휴양", "인스타", "봄", "여름", "가을", "겨울"};
            List<Tag> tagList = new ArrayList<>();
            for (String stag : tagArr) {
                Tag tag = Tag.builder().name(stag).build();
                tagList.add(tag);
                em.persist(tag);
            }

            /*test 유저 1,2 생성*/
            /*userService.create("test1","test1","test1@email.com", "test1");
            userService.create("test2","test2","test2@email.com", "test2");*/

        }
    }
}