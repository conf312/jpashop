### @DiscriminatorColumn
Item 클래스의 하위 클래스를 식별하는 데 사용될 컬럼을 지정하고, `@DiscriminatorValue`는 각 하위 클래스가 해당 컬럼에 저장될 값을 명시합니다.

따라서 데이터베이스 테이블에서 `DTYPE` 컬럼에 'A'값을 가진 레코드는 Album 인스턴스에 해당하고, 'B'값을 가진 레코드는 Book의 인스턴스에 해당합니다.