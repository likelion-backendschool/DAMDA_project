## 팀 구성원, 개인 별 역할

---

[팀장] 김민재 - 여행 설계 구현

박재현 - 여행지 탐색 구현

정혜수 - 여행지 탐색 구현

장동익 - 로그인 / 회원가입 / 마이페이지 구현

좌창화 - 여행 설계 구현

## 팀 내부 회의 진행 회차 및 일자

---

8회차(2022.08.29) 디스코드

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

- 좌창화
    - 채팅 메시지를 DB에 저장 후 불러오기 위해 엔티티 생성 및 연관관계 설정
    - JPA 기능 구현 중
- 박재현
    - 공통 프로젝트 설정 파일(application.properties)을 yml 형식의 파일로 변경
        
        추후 DB 외 다른 설정이 추가될 수 있음.
        
        ```yaml
        spring:
          profiles:
            include: db
        ```
        
    - 개인 DB 설정 파일(개인 개발 환경에 맞게 설정)
        
        ```yaml
        spring:
          h2:
            console:
              enabled: true
              path: /h2-console
          datasource:
            url: jdbc:h2:tcp://localhost/~/damda
            driverClassName: org.h2.Driver
            username: sa
            password:
          jpa:
            properties:
              hibernate:
                dialect: org.hibernate.dialect.H2Dialect
            hibernate:
              ddl-auto: create
        ```
        
    - devbranch에 공통 템플릿 merge

        <img width="662" alt="스크린샷 2022-08-29 오후 10 27 29" src="https://user-images.githubusercontent.com/97084128/187212129-2606ea41-39e8-411f-9b0c-9ce9bea30306.png">


    - 검색 기능 향상(여행지 이름 / 지역 / 주소 / 리뷰)
        
        ```java
        private Specification<Spot> search(String kw) {
                return new Specification<>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public Predicate toPredicate(Root<Spot> s, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        query.distinct(true);  // 중복을 제거
                        Join<Spot, Review> r = s.join("reviewList", JoinType.LEFT);
                        return cb.or(cb.like(s.get("name"), "%" + kw + "%"),
                                cb.like(s.get("city"), "%" + kw + "%"),
                                cb.like(s.get("address"), "%" + kw + "%"),
                                cb.like(r.get("content"), "%" + kw + "%"));
                    }
                };
            }
        ```
        
- 김민재
    - 공통탬플릿 안에 지도 삽입


        <img width="675" alt="스크린샷 2022-08-29 오후 10 27 59" src="https://user-images.githubusercontent.com/97084128/187212214-aae10062-8a1b-4f6a-adb5-ec31a39fd38b.png">
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

- git 을 어떻게 잘 활용하여 팀 프로젝트를 진행할 수 있을지

## 개발 결과물 공유

---

Github Repository URL:  https://github.com/likelion-backendschool/DAMDA_project

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂

<img width="721" alt="스크린샷 2022-08-29 오후 10 28 34" src="https://user-images.githubusercontent.com/97084128/187212318-f80486d0-a14e-49b9-aefb-e631279b8255.png">

<img width="721" alt="스크린샷 2022-08-29 오후 10 28 51" src="https://user-images.githubusercontent.com/97084128/187212392-1bda20bf-9841-4a19-9669-9b070e4419c8.png">

