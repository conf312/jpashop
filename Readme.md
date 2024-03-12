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

### 연관 관계 참조 및 조인
실무 JPA에서는 @OneToMany, @ManyToMany 조인 사용을 지양한다. (@ManyToOne 도 되도록이면 사용하지 않는다고 한다.)

DB에 관계를 맺는 순간 규모가 커질 수록 수정에 제약이 따르고, 양방향 연관관계에서는 무한루프 등, 특히 JPA에서는 영속성 전이나 고아객체에 대한 지식이 부족하면 미궁에 빠지기 쉽상이기 때문이다.

하지만, 알고 사용하지 않는거와 사용해보지 않고 사용하지 말라하여 사용하지 않는것은 천지차이 이기 때문에 이 프로젝트에서는 다양한 연관 관계 조인 방식을 사용해보며 학습한다.

### 어노테이션
#### @Transactional(readonly = true)
데이터를 변경하지 않는 트랜잭션에서 플러시를 생략하여 약간의 성능 향상을 얻을 수 있다.

#### @DiscriminatorColumn 
Item 클래스의 하위 클래스를 식별하는 데 사용될 컬럼을 지정하고, `@DiscriminatorValue`는 각 하위 클래스가 해당 컬럼에 저장될 값을 명시합니다.

따라서 데이터베이스 테이블에서 `DTYPE` 컬럼에 'A'값을 가진 레코드는 Album 인스턴스에 해당하고, 'B'값을 가진 레코드는 Book의 인스턴스에 해당합니다.
