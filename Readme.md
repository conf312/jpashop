## 영속성 컨텍스트
![image](https://github.com/conf312/jpashop/assets/13326651/1a55cb27-ea35-46d3-b9c8-2a319639ab33)

### 비영속
영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
```java
Member member = new Member("kim");
```

### 영속
영속성 컨텍스트에 관리되는 상태
```java
Member member = new Member("kim");

EntityMangager em = entityManagerFactory.createEntityManager();
em.persist(member); // 이제 영속성 컨텍스트에서 관리한다.
```
em.persist(member); 를 한다고 DB에 저장되는 것은 아니다. `커밋` 혹은 `flush()` 이후에 DB에 저장된다. 그전까지는 영속성 컨텍스트에만 존재.

### 준영속

영속성 컨텍스트에 저장되었다가 분리된 상태
```java
Member member = new Member("kim");

EntityMangager em = entityManagerFactory.createEntityManager();
em.detach(member);
```
개발자가 직접 준영속을 만들 일은 거의 없음

## 트랜잭션
- em.persist() 트랜잭션이 커밋되면서 영속성 컨텍스트가 플러시 된다. 이때 트랜잭션과 영속성 컨텍스트가 종료된다.
- em.findById() 저장한 엔티티를 조회하면 새로운 영속성 컨텍스트가 생성된다.



## 연관 관계 참조 및 조인
실무 JPA에서는 @OneToMany, @ManyToMany 조인 사용을 지양한다. (@ManyToOne 도 되도록이면 사용하지 않는다고 한다.)

DB에 관계를 맺는 순간 규모가 커질 수록 수정에 제약이 따르고, 양방향 연관관계에서는 무한루프 등, 특히 JPA에서는 영속성 전이나 고아객체에 대한 지식이 부족하면 미궁에 빠지기 쉽상이기 때문이다.

하지만, 알고 사용하지 않는거와 사용해보지 않고 사용하지 말라하여 사용하지 않는것은 천지차이 이기 때문에 이 프로젝트에서는 다양한 연관 관계 조인 방식을 사용해보며 학습한다.

## 엔티티 그래프
엔티티 조회시점에 연관된 엔티티들을 함께 조회하는 기능이다.

### 1. Named 엔티티 그래프
### @NamedEntityGraph로 정의한다.
```java
@NamedEntityGraph(name = "Order.withMember", attributeNodes = {@NamedAttributeNode("member"})
@Entity
public class Order {
...
}
```
### 엔티티 그래프 사용
```java
EntityGraph graph = em.getEntityGraph("Order.withMember");
Map hints = new HashMap();
hints.put("javax.persistence.fetchgraph", graph);

Order order = em.find(Order.class, orderId, hinst);
```

### 2. JPQL에서 엔티티 그래프
```java
List<Order> resultList =
  em.createQuery("select o from Order o where o.id = :orderId", Order.class
  .setParameter("orderId", orderId)
  .setHint("javax.persistence.fetchgraph", e.getEntityGraph("Order.withAll"))
  .getResultList();
```

## 어노테이션
### @Transactional(readonly = true)
데이터를 변경하지 않는 트랜잭션에서 플러시를 생략하여 약간의 성능 향상을 얻을 수 있다.

### @DiscriminatorColumn 
Item 클래스의 하위 클래스를 식별하는 데 사용될 컬럼을 지정하고, `@DiscriminatorValue`는 각 하위 클래스가 해당 컬럼에 저장될 값을 명시합니다.

따라서 데이터베이스 테이블에서 `DTYPE` 컬럼에 'A'값을 가진 레코드는 Album 인스턴스에 해당하고, 'B'값을 가진 레코드는 Book의 인스턴스에 해당합니다.

## N+1
지연로딩을 사용하되, JPQL 페치 조인을 사용하자.

- @OneToOne, @ManyToOne: 기본 페치 전략은 즉시로딩
- @OneToMany, @ManyToMany: 기본 페치 전략은 지연로딩

따라서 기본값이 즉시 로딩인 @OnetoOne, @ManyToOne은 `fetch = FetchType.LAZY`로 설정해서 지연 로딩 전략을 사용하도록 하자.

## 읽기 전용 쿼리 성능 최적화
엔티티가 영속성 컨텍스트에 관리되면 1차 캐시부터 변경 감지까지 얻을 수 있는 혜택이 더 많다. 하지만 영속성 컨텍스트는 변경감지를 위해 스냅샷 인스턴스를 보관하므로 더 많은 메모리를 사용하는 단점이 있다. 예를 들어 100건의 구매 내용을 출력하는 단순환 조회가 있다고 가정해보자. 그리고 조회한 엔티티를 다시 조회할 일도 없고 수정할 일도 없이 딱 한번만 화면에 출력하면 된다. 이때는 읽기 전용으로 엔티티를 조회하면 메모리 사용량을 최적화 할 수 있다.

### @Transactional(readOnlny = true)
스프링 프레임워크가 하이버네이트 세션의 플러시 모드를 MANUAL로 설정한다. 이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다. 따라서 트랜잭션을 커밋해도 영속성 컨텍스트를 플러시 하지않는다. 영속성 컨텍스트를 플러시 하지 않으니 등록, 수정, 삭제는 당연히 동작하지 않는다. 하지만 플러시할 때 일어나는 스냅샷 비교와 같은 무거운 로직들을 수행하지 않으므로 성능이 향상된다. 트랜잭션 과정은 이루어지되 단지 영속성 컨텍스트를 플러시하지 않을 뿐이다.


## @PersistenceContext 란?
- EntityManager를 빈으로 주입할 때 사용하는 어노테이션
- 스프링에서는 영속성 관리를 위해 EntityManager가 존재
- 스프링 컨테이너가 시작될 때 EntityManager를 만들어서 빈으로 등록
- 이 때 스프링이 만들어둔 EntityManager를 주입받을 때 사용
- @PersistenceContext로 지정된 프로퍼티에 아래 두 가지 중 한 가지로 EntityManager를 주입
- EntityManagerFactory에서 새로운 EntityManager를 생성하거나 Transaction에 의해 기존에 생성된 EntityManager를 반환

### @PersistenceContext를 사용해야 하는 이유
#### EntityManager를 사용할 때 주의해야 할 점
여러 쓰레드가 동시에 접근하면 동시성 문제가 발생하여 쓰레드 간에는 EntityManager를 공유해서는 안됩니다. 하지만 스프링은 싱글톤 기반으로 동작하기에 빈은 모든 쓰레드가 공유됩니다.

**그러나 @PersistenceContext으로 EntityManager를 주입받아도 동시성 문제가 발생하지 않는 이유는**
- 스프링 컨테이너가 초기화되면서 @PersistenceContext으로 주입받은 EntityManager를 Proxy로 감쌉니다.
- 그리고 EntityManager 호출 시 마다 Proxy를 통해 EntityManager를 생성하여 Thread-Safe를 보장합니다.
