package com.ll.exam.damda;

import com.ll.exam.damda.entity.search.Spot;
import com.ll.exam.damda.entity.search.Tag;
import com.ll.exam.damda.repository.search.review.ReviewRepository;
import com.ll.exam.damda.repository.search.spot.SpotRepository;
import com.ll.exam.damda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
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

            createSpot("숙성도 노형점", "제주", "제주특별자치도 제주시 원노형로 41", "숙성 돼지고기 맛집! 줄서서 먹는 집!"
                    , 316010726, "126.48502796132595", "33.48503198254993", "Y", "/images/thumbnail/숙성도노형점.png");

            createSpot("성산일출봉", "제주", "제주특별자치도 서귀포시 성산읍 성산리 78", "높이 180M. 제주특별자치도 동쪽에 돌출한 성산반도 끝머리에 있다."
                    , 25285071, "126.940537521366", "33.4591349705437", "Y", "/images/thumbnail/성산일출봉.png");

            createSpot("색달식당 중문갈치조림구이 본점", "제주", "제주특별자치도 서귀포시 예래로 255-18", "제주 앞바다에서 잡은 제주도 갈치! 꼭 들려서 맛있는 통갈치조림과 구이로 건강한 여행하세요."
                    , 17809788, "126.38624441353717", "33.24181180984976", "Y", "/images/thumbnail/색달식당중문갈치조림구이본점.png");

            createSpot("델문도", "제주", "제주특별자치도 제주시 조천읍 조함해안로 519-10", "맛있는 베이커리가 있는 함덕 델문도 카페"
                    , 26867476, "126.668723957376", "33.5437093358268", "Y", "/images/thumbnail/델문도.png");

            createSpot("카페나모나모", "제주", "제주특별자치도 제주시 도두봉6길 4", "오션뷰 대형 로스터리& 베이커리카페 입니다."
                    , 373077516, "126.471971567284", "33.5088944148681", "Y", "/images/thumbnail/카페나모나모.png");

            createSpot("우무", "제주", "제주특별자치도 제주시 한림읍 한림로 542-1", "'우무'는 제주 해녀가 채취한 우뭇가사리를 오랜 시간 끓여 만든 제주 푸딩입니다."
                    ,1152497778 , "126.256445696105", "33.4058898742306", "Y", "/images/thumbnail/우무.png");

            createSpot("애월더선셋", "제주", "제주특별자치도 제주시 애월읍 일주서로 6111", "해지는 모습을 볼 수 있는 제주 애월 카페"
                    ,27149644 , "126.30887594187952", "33.4561265802926", "Y", "/images/thumbnail/애월더선셋.png");

            createSpot("울트라마린", "제주", "제주특별자치도 제주시 한경면 일주서로 4611", "에메랄드빛 바다와 파도, 노을이 예쁘게 보이는 곳, 뷰맛집. 사진맛집"
                    ,1915903142 , "126.20606435314039", "33.36946377010182", "Y", "/images/thumbnail/울트라마린.png");

            createSpot("우진해장국", "제주", "제주특별자치도 제주시 서사로 11", "제주산 고사리를 갈아 넣고 푹 끓여 갈색 빛깔이 나는 제주식 해장국을 만날 수 있는 한식당입니다."
                    ,11547525 , "126.52000128027187", "33.51151689656457", "Y", "/images/thumbnail/우진해장국.png");

            createSpot("제주김만복 본점", "제주", "제주특별자치도 제주시 오라로 41", "제주도김밥 전복내장으로 만든 김만복김밥 본점"
                    ,1046180098 , "126.508921919644", "33.4970353986304", "Y", "/images/thumbnail/제주김만복본점.png");

            createSpot("고집돌우럭 함덕점", "제주", "제주특별자치도 제주시 조천읍 신북로 491-9", "- 제주도 '최초' 리뷰 20,000개 달성\n" +
                            "- 네이버가 선정한 제주 맛집 TOP10\n" +
                            "- 네이버&카카오&구글 리뷰 제주도 내 최다, 최고평점(4.8점)"
                    ,28082185 , "126.66309886995431", "33.54381497240532", "Y", "/images/thumbnail/고집돌우럭함덕점.png");

            createSpot("자매국수 본점", "제주", "제주특별자치도 제주시 탑동로11길 6", "깔끔한 고기국수 맛집!"
                    ,21455793 , "126.51700772779006", "33.516815625143686", "Y", "/images/thumbnail/자매국수본점.png");

            createSpot("이춘옥의원조고등어쌈밥", "제주", "제주특별자치도 제주시 애월읍 일주서로 7213", "애월 갈치조림 맛집 보다 맛있는 묵은지고등어찜"
                    ,24780480 , "126.418698640025", "33.4889959115397", "Y", "/images/thumbnail/이춘옥의원조고등어쌈밥.png");

            createSpot("한라산국립공원", "제주", "제주특별자치도 제주시 오등동 산 182", "높이 1,950m이다. 남한에서 가장 높은 산이다. 제3기 말∼제4기 초에 분출한 휴화산이다. 현무암으로 이루어져 있으며 줄기는 제주도 중앙에서 동서로 뻗는다."
                    ,21135119 , "126.54222094512", "33.3766655632143", "Y", "/images/thumbnail/한라산국립공원.png");

            createSpot("협재해수욕장", "제주", "제주특별자치도 제주시 한림읍 한림로 329-10", "물이 맑고 투명하기로 소문난"
                    ,8159415 , "126.239157539085", "33.3938660776221", "Y", "/images/thumbnail/협재해수욕장.png");

            createSpot("섭지코지", "제주", "제주특별자치도 서귀포시 성산읍 섭지코지로 107", "신양해수욕장에서 2㎞에 걸쳐 바다를 향해 길게 뻗어 있다. 섭지란 재사(才士)가 많이 배출되는 지세란 뜻이며 코지는 곶을 뜻하는 제주방언이다."
                    ,8413659 , "126.930609241011", "33.4239380655993", "Y", "/images/thumbnail/섭지코지.png");

            createSpot("천지연폭포", "제주", "제주특별자치도 서귀포시 서홍동 2565", "길이 22m, 너비 12m(물이 많을 때), 못의 깊이 20m. 조면질(粗面質) 안산암으로 이루어진 기암 절벽에서 세찬 옥수가 떨어지는 경승지이다."
                    ,8100102 , "126.55452234287986", "33.24721231608811", "Y", "/images/thumbnail/천지연폭포.png");

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