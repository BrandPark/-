# MSA (Microservice Architecture)

`MSA`는 **시스템을 여러개의 독립된 서비스로 나눠서 조합하여 기능을 제공하는 구조**를 말합니다.

전통적인 방식(Monolith)의 문제점인 Scale-out의 어려움과 단일 장애지점(Single Point Of Failure)를 해결할 수 있는 유연한 구조입니다.

서비스는 기능 중심으로 나뉘고, **각 서비스는 Network로 통신하여 기능을 수행**합니다.

![image](https://user-images.githubusercontent.com/53790137/152491304-6885e958-9003-4313-9a0d-fce24301f500.png)

## 왜 MSA를 사용하나?

### 기존 방식
모놀리스 구조의 단점은 다음과 같습니다.

1. 하나의 장애가 발생하면 **전체 서비스에 장애가 전파**된다. (ex. DB에 문제가 발생하면 모든 서비스가 중단)
2. 개발팀과 운영팀 또는 서로 다른 서비스의 개발팀 간의 소통시간이 필요하여 **배포 주기가 느립니다.** 즉, 서비스의 품질 향상 속도가 느립니다.
3. **하나의 DB를 사용**합니다. RDBMS를 사용하는 경우 서비스 간의 의존성이 많아서 DB 서버를 Scale-out하기 힘듭니다. 

모놀리스도 NoSQL이나 캐시를 사용하여 성능을 향상시킬 수 있습니다. 

그럼에도 기업들이 MSA를 사용하는 이유는 다음과 같습니다. 

### MSA
1. 하나의 서비스에 장애가 발생하면 **해당 서비스에서 장애가 끝납니다.** 그래서 빠른 복구가 가능하고 Circuit breaker의 Fallback과 같은 기술을 사용하면 사용자는 장애가 일어난지 모르게도 할 수 있습니다.
2. 각 서비스의 배포는 독립적으로 이루어지기 때문에 **배포 주기가 빠릅니다.** 또 단위 테스트 또한 빨라서 CI/CD가 빠릅니다.
3. **서비스별로 DB를 따로 사용**할 수 있습니다. 각 서비스는 공유되는 상태가 없기 때문에 DB를 나누어 사용할 수 있습니다.
4. 각 팀별로 필요에 따라 서로 다른 기술을 도입할 수 있습니다. 
5. 각 서비스별로 인프라를 따로 증설할 수 있습니다. 트래픽이 높은 서비스만 서버를 증설하여 효과적으로 인프라 자원을 사용할 수 있습니다. 

TPS가 높은 큰 서비스의 경우 단일 시스템으로 모든 트래픽을 감당할 수 없습니다. 

특히 배달의 민족과 같은 B2C 서비스는 장애에 민감하기 때문에 MSA와 MQ를 도입하여 트래픽을 처리했다고 합니다.

정리하면 `MSA`는 **서비스를 나누어 장애지점을 나누고 팀도 나누어 효과적인 장애 대응과 빠른 제품 품질 향상을 얻을 수 있는 설계 방식**입니다.

## MSA의 단점

- **설계가 어렵습니다.** `MSA`는 서비스가 완전히 분리되어 있기 때문에 설계를 잘못하면 트랜잭션 처리에 애를 먹을 수 있습니다. 
- **테스트가 복잡해질 수 있습니다.** 서비스가 분리되어 있어 통합 테스트가 복잡해집니다.
- **서비스 간의 통신 매커니즘이 복잡할 수 있습니다.**

## 참고
- [[토크ON세미나] Spring Cloud를 활용한 MSA](https://www.youtube.com/watch?v=iHHuYGdG_Yk)