## 팀 구성원, 개인 별 역할

---

[팀장] 김민재 - 여행 설계 구현

박재현 - 여행지 탐색 구현

정혜수 - 여행지 탐색 구현

장동익 - 로그인 / 회원가입 / 마이페이지 구현

좌창화 - 여행 설계 구현

## 팀 내부 회의 진행 회차 및 일자

---

7회차(2022.08.22) 디스코드, 재현님 불참

## 현재까지 개발 과정 요약 (최소 500자 이상)

---

- 장동익
    - 회원가입, 로그인 구현 완료 (세션을 이용한 로그인 로그아웃 관리)
    - 회원가입시 에러메세지 순서가 랜덤하게 출력되는 문제 발생 해결 중
    - 자동로그인 구현 예정 (세션+쿠키 or JWT)
    - 마이페이지 구현 예정
    - User 테이블 일부 수정


    <img width="694" alt="스크린샷 2022-08-22 오후 10 33 21" src="https://user-images.githubusercontent.com/97084128/185933615-54b1c9c8-b1a2-4027-a725-1cbe088a8732.png">

    - 박재현
    - 공통 템플릿 적용
        - 여행지 검색 페이지의 경우 카드 형식으로 여행지 노출 방식 선택
    - 여행지 DB 초기화 방식 구현 (테스트 데이터 100개)
    - 여행지 / 리뷰 / 태그 엔티티 구현
    - 여행지 이름 검색 기능 구현
        - 기본 정렬 방식 : 리뷰 갯수 내림차순 정렬
    - 여행지 태그 검색 기능 구현
        - 관련 태그 합계로 검색 리스트 정렬
    - 스프링 부트 페이징 기능 적용
    
    
- 김민재
    - spring, thymeleaf 공부 중
    - 유튜브와 블로그 등을 통해 장바구니 기능을 어떻게 구현할지 탐색, 공부 중
    

## 개발 과정에서 나왔던 질문 (최소 200자 이상)

---

- 프로젝트를 진행할 때 mysql과 mariaDB 버전을 어떤 것을 사용해야 하는가(버전별 호환성 문제 등..)
    1. mysql8과 mariaDB 10의 백업 방식의 차이
        1. [https://yhjin.tistory.com/73?category=1014637](https://yhjin.tistory.com/73?category=1014637)
        2. [https://yhjin.tistory.com/74?category=1014637](https://yhjin.tistory.com/74?category=1014637)
        3. [https://yhjin.tistory.com/75](https://yhjin.tistory.com/75)
    2. MySQL 5.7 과 호환되는 버전이 10.1과 10.2 인데 10.1 은 이제 곧 support 가 끝나고, 10.2 는 아직 1년 반이 남음. 10.3 은 5.7 과 8.0 에 걸쳐 있는데 이 버전의 support 는 2023년 종료
    
    <img width="628" alt="스크린샷 2022-08-22 오후 10 34 10" src="https://user-images.githubusercontent.com/97084128/185933793-b819ca47-c41c-4820-a73e-010f0e164ef9.png">

    1. [https://mariadb.com/kb/en/mariadb-vs-mysql-compatibility/#incompatibilities-between-currently-supported-mariadb-versions-and-mysql](https://mariadb.com/kb/en/mariadb-vs-mysql-compatibility/#incompatibilities-between-currently-supported-mariadb-versions-and-mysql)
    2. 추가로 적어주세요
    

- PR은 언제 하면 될지
    - 스프링 시큐리티가 다른 기능 개발 과정에 영향을 미칠 수 있으므로 로그인/회원가입을 가장 먼저 하든지 마지막에 하든지 해야할 것 같음

## 개발 결과물 공유

---

개발 결과물 있으시면 여기에도 캡쳐해서 공유해주세요!

- 로그인 페이지
- 회원가입 페이지

<img width="451" alt="스크린샷 2022-08-22 오후 10 35 10" src="https://user-images.githubusercontent.com/97084128/185934011-f6fa0f0c-c474-4977-b379-4578bce33369.png">

- 여행지 검색 페이지_1

<img width="740" alt="스크린샷 2022-08-23 오후 5 58 23" src="https://user-images.githubusercontent.com/97084128/186117122-9e372660-fae9-443e-88bd-44cc0a0f1bf4.png">


- 여행지 검색 페이지_2

<img width="733" alt="스크린샷 2022-08-23 오후 5 58 44" src="https://user-images.githubusercontent.com/97084128/186117201-6e2192b7-8311-4881-98ce-9197f0707a6a.png">


<img width="706" alt="스크린샷 2022-08-22 오후 10 35 29" src="https://user-images.githubusercontent.com/97084128/185934080-aa11912d-f504-4ceb-8758-c86938961ab6.png">

