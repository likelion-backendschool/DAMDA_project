## 팀 구성원

---

- [팀장] 김민재 박재현 정혜수 장동익 좌창화(불참)

## 회고 내용 요약 (최소 500자 이상)

---

# Spring vs Spring Boot

## Spring = ‘개발자들의 겨울은 끝났다.’

![스크린샷 2022-08-11 오후 10.52.35.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a7ee4ecc-cdd0-4865-af08-5187490dd28f/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-11_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_10.52.35.png)

차이점

## dependency

spring

- 길다.

spring boot

- 모든 dependency 를 버전까지 명확하게 정의해준다. (권장 버전으로 자동 설정)

## configuration

### spring

- 길었다….

### spring boot

- application.properties
    
    ![스크린샷 2022-08-11 오후 10.55.31.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/c0cbbe78-b258-42ef-a2ac-af873c126253/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-11_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_10.55.31.png)
    
- application.yml - 사람과 가깝다.(요즘 자주 쓰이고 있음)
    
    ![스크린샷 2022-08-11 오후 10.55.42.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6951ce18-3ba4-4f6a-94ac-7784ede0c10e/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-11_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_10.55.42.png)
    

## embedded server

![스크린샷 2022-08-11 오후 11.00.16.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/0cf67d0d-b5f9-46ca-9022-988c40fa0678/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-11_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_11.00.16.png)

내장 톰캣 서버로 인해 서버 구동 시간이 절반 가까이 단축되었다.

- java -jar $REPOSITORY/$JAR_NAME &
    - 내장 서블릿 컨테이너 덕분에 jar파일로 간단히 배포 가능
    

# 결론

### spring boot는 개발자들이 개발에만 더욱 집중할 수 있도록 해준다.

# 트랜잭션

## 트랜잭션, Rollback, Commit

SQL 쿼리들을 하나로 묶어놓은 작업수행의 `논리적 단위`

 

중간에 실행이 중단될 경우 `Rollback`

오류없이 실행을 마치면 `commit`

## 트랜잭션 예시

송금 과정을 하나의 트랜잭션이라고 생각해보자

- 내 계좌에서 10000원을 차감한다.
- 친구의 계좌에서 10000원을 증가한다.

만약 오류로 인해 내 계좌에서는 10000원이 차감됐지만 친구의 계좌에서 10000원이 증가되지 않았다면? `Rollback`을 통해 처음부터 다시 송금을 시작한다.

만약 송금이 정상적으로 됐다면 `commit`을 한다

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/4a38b5f9-6c1c-41a9-8301-754874b642a2/Untitled.png)

## 트랜잭션의 특성

- **원자성 ( Atomicity )**
    - 트랜잭션의 작업이 부분적으로 실행되거나 중단되지 않는 것을 보장하는 것을 말합니다.
    - 즉, All or Noting의 개념으로서 작업 단위를 일부분만 실행하지 않는다는 것을 의미합니다.
- **일관성 ( Consistency )**
    - **트랜잭션이 성공적으로 완료되면 일관적인 DB상태를 유지하는 것을 말합니다.**
    - 여기서 말하는 일관성이란, 위의 송금 예제에서 금액의 데이터 타입이 정수형(integer)인데, 갑자기 문자열(string)이 되지 않는 것을 말합니다.
        - 즉, 송금 전후 모두 금액의 데이터 타입은 정수형이여야 한다는 것이 일관성입니다.
- **격리성 ( Isolation )**
    - **트랜잭션 수행시 다른 트랜잭션의 작업이 끼어들지 못하도록 보장하는 것을 말합니다.**
    - **즉, 트랜잭션끼리는 서로를 간섭할 수 없습니다.**
- **지속성 ( Durability )**
    - **성공적으로 수행된 트랜잭션은 영원히 반영이 되는 것을 말합니다.**
    - **commit을 하면 현재 상태는 영원히 보장됩니다.**

## Recovery

### Checkpoint와 Redo Undo

로그정보를 기반으로 이전 체크포인트부터 다시 수행

체크포인트 이전 트랜잭션  → 회복 과정 제외

체크포인트 이후 완료 → `Redo` : 체크포인트 이후부터 재실행

체크포인트 이후 진행중 → `Undo` : 취소

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/bd49527e-e609-48a8-bf1c-c574a65adc77/Untitled.png)

## 우리 프로젝트에서 트랜잭션이?

### 테스트케이스 트랜젝션화

- 테스트 메서드에 `@Transactional`을 사용하면 트랜잭션으로 감싸짐
- 단, Spring Test에서의  @Transactional의 default는 rollback true

```java
@Test
@Transactional
@Rollback(false)
void question으로부터_관련된_질문들_조회() {
        // SELECT * FROM question WHERE id = 1
        Question q = questionRepository.findById(
				~~
```

`@Rollback(false)` 이걸 안하면 테스트 케이스가 잘 수행됐는지 DB상에서 볼 수가 없음

근데 `@commit` 코드를 열어보면

```java
@Rollback(false)

public @interface Commit {

} 
```

즉, @commit = @rollback(false)라고 생각하면 됨

<aside>
❓ `Auto Increment` 옵션은 트랜잭션의 범위 밖에서 동작, 트랜잭션에 포함된 `insert`작업으로 인해 증가한 `id`는 트랜잭션이 롤백되어도 다시 감소하지 않음

</aside>

# DTO vs VO vs Entity

### 혼란의 시작

---

⌜Core J2EE Patterns: Best Practices and Design Strategies⌟ 책의 초판에서는 데이터 전송용 객체를 `VO`로 정의했다. 그 이후 2판에서는 해당 객체를 `TO`로 정정해서 작성했다. 이 때문에 DTO와 VO를 혼동하게 된 것 같다.

### ****DTO(Data Transfer Object)****

---

DTO는 데이터를 전달하기 위한 객체이다. 계층간 데이터를 주고 받을 때, 데이터를 담아서 전달하는 바구니로 생각할 수 있다. 여러 레이어 사이에서 DTO를 사용할 수 있지만, 주로 View와 Controller 사이에서 데이터를 주고 받을 때 활용한다.

DTO는 getter/setter 메소드를 포함한다. 이 외의 비즈니스 로직은 포함하지 않는다.

아래 코드처럼 `setter`를 가지는 경우 가변 객체로 활용할 수 있다.

```java
public class MemberDto {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

한편, setter가 아닌 `생성자`를 이용해서 초기화하는 경우 불변 객체로 활용할 수 있다. 불변 객체로 만들면 데이터를 전달하는 과정에서 데이터가 변조되지 않음을 보장할 수 있다.

```java
public class MemberDto {
    private final String name;
    private final int age;

    public MemberDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

### **VO(Value Object)**

---

VO는 값 자체를 표현하는 객체이다. VO는 객체들의 주소가 달라도 값이 같으면 동일한 것으로 여긴다. 예를 들어, 고유번호가 서로 다른 만원 2장이 있다고 생각하자. 이 둘은 고유번호(주소)는 다르지만 10000원(값)은 동일하다.VO는 `getter` 메소드와 함께 비즈니스 로직도 포함할 수 있다. 단, `setter` 메소드는 가지지 않는다. 또, 값 비교를 위해 `equals()`와 `hashCode()` 메소드를 오버라이딩 해줘야 한다.

아래 코드처럼 equals()와 hashCode() 메소드를 오버라이딩 하지 않으면 테스트가 실패한다.

```java
// Money.java
public class Money {
    private final String currency;
    private final int value;

    public Money(String currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }
}

// MoneyTest.java
public class MoneyTest {
    @DisplayName("VO 동등비교를 한다.")
    @Test
    void isSameObjects() {
        Money money1 = new Money("원", 10000);
        Money money2 = new Money("원", 10000);

        assertThat(money1).isEqualTo(money2);
        assertThat(money1).hasSameHashCodeAs(money2);
    }
}
```

다음은 equals()와 hashCode() 메소드를 오버라이딩 하지 않았을 때의 테스트 결과이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/51a13a4e-d109-4c8e-bc39-2f0305e7d0f7/Untitled.png)

한편, 두 메소드를 오버라이딩 하면 테스트가 통과한다. 앞서 말했듯이 VO는 주소가 아닌 값을 비교하기 때문이다.

```java
// Money.java
public class Money {
    private final String currency;
    private final int value;

    public Money(String currency, int value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, value);
    }
}

// MoneyTest.java
public class MoneyTest {
    @DisplayName("VO 동등비교를 한다.")
    @Test
    void isSameObjects() {
        Money money1 = new Money("원", 10000);
        Money money2 = new Money("원", 10000);

        assertThat(money1).isEqualTo(money2);
        assertThat(money1).hasSameHashCodeAs(money2);
    }
}
```

다음은 두 메소드를 오버라이딩 했을 때의 테스트 결과이다.

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a05716ec-96dc-4ffb-8c19-2d4dcb496e34/Untitled.png)

### Entity

---

Entity는 실제 DB 테이블과 매핑되는 핵심 클래스이다. 이를 기준으로 테이블이 생성되고 스키마가 변경된다. 따라서, 절대로 Entity를 요청이나 응답값을 전달하는 클래스로 사용해서는 안 된다.Entity는 id로 구분된다. 그리고 비즈니스 로직을 포함할 수 있다.

Entity는 DTO처럼 `setter`를 가지는 경우 가변 객체로 활용할 수 있다.

```java
public class Member {
    private final Long id;
    private final String email;
    private final String password;
    private final Integer age;

    public Member() {
    }

    public Member(Long id, String email, String password, Integer age) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
```

	

| 분류 | DTO | VO | ENTITY |
| --- | --- | --- | --- |
| 정의 | 레이어간 데이터 전송용 | 객체	값 표현용 객체 | DB 테이블 매핑용 객체 |
| 상태 변경 여부 | 가변 또는 불변 객체 | 불변 객체 | 가변 또는 불변 객체 |
| 로직 포함 여부 | 로직을 포함할 수 없다. | 로직을 포함할 수 있다. | 로직을 포함할 수 있다. |

## 회고 과정에서 나왔던 질문 (최소 200자 이상)

---

- DTO와 VO의 사용 목적에 차이가 있는지?
    - Answer
        
        [OKKY | DTO, VO 개념이 잘 잡히지 않습니다..](https://okky.kr/article/325370)
        
        - VO : 데이터 그 자체로 의미 있는 것들
            
            색상으로 예를 들어보겠습니다. 색상 중에 빨간이나 초록 같은 경우에는 어떨까요? RGBA(255,0,0,0) 이고 RGBA(0,255,0,0) 로 표현됩니다.더 나아가서 코드명이 붙은 색상도 존재할 겁니다. 예를 들어 페라리가 사용하는 레드는 특허가 있다고 들었습니다.(사실 확인 안함) 그럼 페라리의 빨간 색은 그 자체만으로 의미가 있는 값이죠.  코드 예제 보시죠.
            
            ```java
            class Color {
            		private int R,G,B,A;
            		public Color(int r,int g, int b, int a){...}
            		//getters and setters
            		public final static Color RED = new Color(255,0,0);
            		public final static Color GREEN = new Color(0,255,0,0);
            		public final static Color FERRARI_Red = new Color(255,40,0,0);
            }
            ```
            
            위와 같은 색상 객체가 있고 빨강과 초록이 그리고 페라리 레드가 있습니다. 코드에서 빨강 사용 하던가, 초록을 사용할 때, Color.RED, Color.GREEN 를 사용하시면 됩니다.
            
            그리고 특별한 경우인 페라리레드 같은 경우에도 해당 색상은 그 자체로 의미 있고 Color.FERRARI_RED로 사용하시면 됩니다. 값 객체라고 하는 의미가 있는 것이죠. (빨간 값 객체는 Color.RED 로 , 초록 값 객체는 Color.GREEN 로 표현 된다. 그리고 페라리 레드는 Color.FERRARI_RED 이다.)
            
        - DTO : 전송되는 데이터의 컨테이너
            
            DTO 는 데이터를 담는 그릇이라고 보시면 됩니다. 보통 직렬화 가능하도록 (Serializable) 정의되지만 그렇지 않는 경우가 현재 업계의 동향입니다. 과거에는 JVM 상에서만 데이터가 이동하기 때문에(서버와 클라이언트가 자바 머신으로만...) 단순히 컨테이너 역할만 수행하면 되어 지도록 단순하게 만들어 졌습니다.
            
            현재의 개발 환경에서 보통의 경우 데이터는 다음과 같이 흐름으로 이동 합니다.
            
            - 서버 측 : Database Record Data -> **DTO** -> API(JSON or XML) -> Client
            - 클라이언트 측 : Server -> API(JSON or XML) -> **DTO** > View or Local Database System
            
            예전의 개발 환경에서는 단순한  자료를 담는 객체로만 사용되어지다 현재의 서버 환경이나 클라이언트 환경에서는 다양한 기능을 추가 하도록 요구 되어집니다. (제약 사항이나 상태값 조회 등)
            
            정리하면 다음과 같습니다.
            
            - VO : 사용 되는 값이 객체로 표현 되어지는 경우에 사용. 값의 변경 없음
            - DTO : 데이터의 전송을 위한 객체, 과거에는 값을 전달하는 데만 사용되었지만 현재는 비지니스 로직 등을 가지고 있는 경우가 많음.
            
            사실상 이렇게 나누는 것도 개그가 될 수 있습니다. 실제로는 VO 가 DTO 처럼 많이 사용 되기 때문입니다. 사실 저도 VO 라고 작명하고 DTO로 사용하기도 합니다. 그냥 편한데로 사용하세요~
            
- 스프링에서 스프링부트로 migration하는 것은 어려운지?
    - Answer
        
        [](https://www.baeldung.com/spring-boot-migration)
        
        대부분의 경우 사용자 지정 컨트롤러 및 기타 ****Components****는 제외한 ****Components****들을 수정해야 한다.
        
        그리고 아래 6가지에 대한 내용은 완벽하게 알아야 문제 없이 migration이 가능 하다.
        
        - **XML Bean 설정 방법**
        - **Java @Bean 어노테이션 설정 방법**
        - **Java -@Component, @ComponentScan 개념 이해**
        - **스프링 DI 기본 개념**
        - **@SpringBootApplication 어노테이션이란?**
        - **Autoconfiguration 개념 이해**
        

## 회고 인증샷 & 팀 자랑

---

- 필수) 팀원들과 함께 찍은 인증샷(온라인 만남시 스크린 캡쳐)도 함께 업로드 해주세요 🙂
- 필수) 자랑 멘트는 **‘팀 내에서 어떻게 복습을 하고 있고, 해당 복습 과정으로 인해 어떤 긍정적인 효과가 발생했는지’**에 대해 간단하게 작성해 주시면 됩니다 😊
    
    ![스크린샷 2022-08-12 오후 8.47.54.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d9c259a3-106c-4f8b-8d77-9a6ebaaa1df1/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-08-12_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_8.47.54.png)
