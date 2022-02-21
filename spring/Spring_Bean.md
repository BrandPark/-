# Bean

`Bean`은 Spring Container에 의해 관리되는 객체를 의미합니다. 

## lifecycle

![image](https://user-images.githubusercontent.com/53790137/154930319-fe4a6e92-51b6-4c41-8d4b-6152a01f9942.png)

위 그림을 간단히 정리하면

1. 스프링 컨테이너 생성
2. Bean Instance 생성
3. 의존관계 주입
4. 초기화 콜백 
   1. Bean Posts Processors의 초기화 전처리
   2. 초기화
   3. 사용자가 생성한 초기화 메서드(ex. @PostConstruct)
   4. Bean posts Processors의 초기화 후처리
5. 빈 사용 준비 완료
6. 소멸 전 콜백
7. 스프링 종료

Spring bean은 객체를 생성하고 의존관계 주입이 다 끝나야지 사용할 준비가 완료됩니다. 
따라서 Bean의 초기화 작업은 의존관계 주입이 모두 완료 된 후에 호출해야 합니다. 초기화 콜백을 통해 개발자는 그것을 캐치하여 초기화를 진행할 수 있습니다. 소멸전 콜백도 마찬가지 입니다.

콜백을 받기 위해 가장 많이 사용하는 방법은 `@PostConstruct`와 `@PreDestroy`가 있습니다.

참고로 Spring의 AOP도 BPP에 의해 수행되어 컨테이너의 빈이 교체됩니다. 

## Bean scope

**1. Singleton scope**

스프링 빈의 기본 스코프로써 스프링 컨테이너가 시작과 종료까지 유지됩니다. 각 서블릿 스레드는 스프링 컨테이너에 있는 싱클톤 스코프 빈을 공유합니다. 

**2. Prototype scope**

빈을 사용 가능하게 만들어서 던져 주고 컨테이너에서 관리하지 않습니다. 즉, 재사용하지 않고 destroy 메서드 또한 호출할 수 없습니다.

**3. Request scope**

HTTP 요청이 하나 들어오고 응답할 때 까지 유지되는 스코프입니다. 각 요청 마다 별도의 빈이 생성되고 관리됩니다.

그 밖에 Session scope, application scope, websocket scope가 있습니다.

## Thrad-safe?

Bean의 기본 스코프는 싱글톤이기 때문에 멀티 쓰레드에서 안전하지 않습니다. 따라서 빈은 statless한 불변 객체여야 합니다. 만약 가변 필드를 가지고 싶다면 `ThreadLocal`을 사용해야 합니다. 

**ThreadLocal은** 스레드 간에 공유되지 않는 저장소로써 멀티 쓰레드 환경에서 안전하게 가변 객체를 사용할 수 있습니다. 쉽게 말해 스레드마다 별도의 저장소를 주는 것입니다.

SpringSecurity의 `SecurityContextHolder.getContext()`를 보면 내부에 `ThreadLocal<SecurityContext>`를 사용하고 있음을 확인할 수 있습니다. 즉, 요청하는 서블릿 스레드마다 서로 다른 인증 정보를 가지게 됩니다. 