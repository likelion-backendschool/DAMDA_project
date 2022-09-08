## 팀 구성원

---

- [팀장] 김민재 박재현 정혜수 장동익 좌창화

## 회고 내용 요약 (최소 500자 이상)

---

[[웹 브라우저에 URL 입력하면 일어나는 일 - 인프라 위주](https://www.youtube.com/watch?v=GAyZ_QgYYYo&list=PLE0hRBClSk5Lcgv3c-I3PH-1LGkv96oYN&index=82)](https://www.notion.so/URL-d95f3f7a94a440a39150e3ecc6233fb1)

[[OAuth 2.0](https://www.youtube.com/watch?v=vo_0PW3V5zU&list=PLuHgQVnccGMA4guyznDlykFJh28_R08Q-&index=2) : 소개, 역할, 등록(1-3)](https://www.notion.so/OAuth-2-0-1-3-2cbb0cab1cf047a3be7fbf86a550ac73)

[****프로그래밍 초식 : DB 트랜잭션 조금 이해하기****](https://www.notion.so/DB-3b31d15cd51c47b9995ab346b4b65ef6)

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

---

### DNS와 GSLB 차이점이 뭔가요?

- DNS
    
    서버의 상태를 알 수 없기 때문에 서비스가 정상/중단 여부를 모름
    
    Round Robin을 이용한 로드밸런싱으로 지역과 떨어진 지역의 서버와도 네트워크를 연결
    
- GSLB
    
    서버의 상태를 알 수 있어서 정상으로 작동하는 서버를 찾아냄
    
    Round Robin방식은 dns와 동일하나 서버의 트래픽을 분석하고 근접한 서버를 찾아내어 부하를 분산
    

### 트랜잭션 격리 수준이란?

- 트랜잭션에서 일관성이 없는 데이터를 허용하도록 하는 수준
- Isolation Level의 종류
    - Read Uncommitted(레벨 0)
    - Read Commited(레벨 1)
    - Repeatable Read(레벨 2)
    - Serializable(레벨 3)
- 낮은 단계의 Isolation Level 이용시 발생하는 현상
    - Dirty Read
    커밋되지 않은 수정 중인 데이터를 다른 트랜잭션에서 읽을 수 있도록 허용할 때 발생하는 현상
    어떤 트랜잭션에서 아직 실행이 끝난지 않은 다른 트랜잭션에 의한 변경 사항을 보게 되는 되는 경우
    - Non-Repeatable Read
    한 트랜잭션에서 같은 쿼리를 두 번 수행할 때 그 사이에 다른 트랜잭션이 값을 수정 또는 삭제함으로써 두 쿼리의 결과가 상이하게 나타나는 비 일관성 현상
    - Phantom Read
    한 트랜잭션 안에서 일정 범위의 레코드를 두 번 이상 읽을 때, 첫 번째 쿼리에서 없던 레코드가 두 번째 쿼리에서 나타나는 현상
    이는 트랜잭션 도중 새로운 레코드가 삽입되는 것을 허용하기 때문에 나타난다.

## 회고 인증샷 & 팀 자랑

---

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- 저희 회고팀은 강사님이 올려주시는 필수영상을 한 주에 3명이 번갈아가며 정리하고, 그 내용을 바탕으로 알게된 점, 궁금했던 점을 나누고 회의합니다. 그로 인해 서로의 cs지식을 조금씩 넓혀가고 있습니다.^^

<img width="703" alt="스크린샷 2022-08-27 오전 12 33 49" src="https://user-images.githubusercontent.com/97084128/186941144-31c2a346-6b9d-4cbe-9966-accc76de3bec.png">

