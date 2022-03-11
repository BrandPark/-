# Spring Transaction Propagation

`Transaction Propagation`은 트랜잭션을 시작하고 일시 중지하는 기준이되는 설정입니다. Spring은 Propagation 설정에 따라 트랜잭션을 가져오거나 생성합니다. 

종류는 다음과 같습니다.

- REQUIRED
- REQUIRES_NEW
- SUPPORTS
- MANDATORY
- NEVER
- NOT_SUPPORTED
- NESTED

### REQUIRED Propagation

- Spring의 기본 Propagation 설정입니다. 
- 활성 트랜잭션이 있다면 해당 트랜잭션에서 참여하여 로직을 수행합니다. 
- 없다면 새 트랜잭션을 생성합니다.

### SUPPORTS Propagation

- 활성 트랜잭션이 있다면 해당 트랜잭션에 참여하여 로직을 수행합니다.
- 없다면 비트랜잭션으로 실행합니다.

### MANDATORY Propagation

- 활성 트랜잭션이 있다면 해당 트랜잭션에 참여하여 로직을 수행합니다.
- 없다면 Spring은 예외를 Throw합니다.

### NEVER Propagation

- 활성 트랜잭션이 있다면 Spring에서 예외가 발생합니다.

### NOT_SUPPORTED Propagation

- 활성 트랜잭션이 있다면 Spring이 트랜잭션을 일시 중지한 다음 트랜잭션 없이 로직을 수행합니다.
- 없다면 트랜잭션 없이 로직을 수행합니다.
- JTATransactionManager는 즉시 사용 가능한 실제 트랜잭션 일시 중단을 지원합니다. 

### REQUIRES_NEW Propagation

- 활성 트랜잭션이 있다면 현재 트랜잭션을 일시 중단하고 새 트랜잭션을 생성합니다.
- 없다면 새 트랜잭션을 생성합니다. 
- 트랜잭션 일시 중단을 위해 JTATransactionManager가 필요합니다.

### NESTED Propagation

- 활성 트랜잭션이 있다면 현재 지점에 Save Point를 표시하고 새로운 트랜잭션을 생성합니다. 즉, 비즈니스 로직 실행에서 예외가 발생하면 트랜잭션이 이 Save Point로 롤백됩니다. 
- 없다면 REQUIRED 처럼 동작합니다. 
- 부모 트랜잭션의 커밋과 롤백에는 영향을 받지만 반대는 영향을 주지 않습니다. 중첩된 자식 트랜잭션들이 롤백돼도 부모 트랜잭션은 정상적으로 커밋될 수 있습니다. 
- DataSourceTransactionManager는 기본적으로 이 Propagation을 지원합니다. JTATransactionManager의 일부 구현도 이것을 지원할 수 있습니다.
- JpaTransactionManager는 JDBC Connection에 대해서만 NESTED를 지원합니다. 
- 그러나 nestedTransactionAllowed 설정을 true로 설정하고 JDBC 드라이버가 save point를 지원하는 경우 JPA 트랜잭션의 JDBC 액세스 코드에서도 동작합니다. 