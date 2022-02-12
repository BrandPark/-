# API Gateway

MSA에서 서비스간의 내부 통신은 Client-side Load-Balancer를 이용하여 통신한다고 했습니다. 

`API Gateway`는 MSA의 **시스템이 외부 Network와 통신할 수 있는 Gateway역할**을 합니다. 

클라이언트는 API Gateway에 요청을 하고 API Gateway는 URL은 분석하여 적절한 서비스에 라우팅합니다.

**만약 사용하지 않는다면 다음과 같은 문제점이 있습니다.**
- 사용자는 필요한 기능마다 다른 URL로 직접 서비스 서버에 요청해야 합니다. 이것은 보안 문제로도 연결이 됩니다.
- 서비스마다 인증/인가 등 공통된 로직을 구현해야 하는 번거로움이 있습니다.
- 프로토콜이 다른 요청에 대한 처리를 각 서비스마다 해야합니다.

## 장점

- 로그나 SSL 보안, 프로토콜 변환, 일정량 이상의 요청 제한, 모니터링 등의 횡단관심사를 한 곳에서 처리할 수 있습니다.
- 프록시 서버 역할을 하여 백엔드 서버 IP Masking을 통해 보안을 강화합니다.
- 사용자는 API Gateway하고만 통신하여 모든 기능을 이용할 수 있습니다.

## 주의점

- API Gateway가 병목지점이 될 수 있습니다. 적절한 Scale-out이 필요하고 Auto-scaling과 같은 클라우드 서비스를 이용하면 편합니다.

## Spring Cloud Juul

- Netflix에서 제공하는 오픈 소스 API Gateway를 Spring Cloud에서 추상화한 모듈입니다.
- Zuul에는 내부에 Hystrix(Circuit Breaker)와 Ribbon(Client-side LB), Eureka client(Service Discovery)가 있어서 Cloud Native하게 관리가 가능합니다.
- 또한 요청을 세마포어나 스레드 풀을 통해 일정량 이상의 요청을 Reject하여 격리시킬 수 있습니다.

## 참고
- [[토크ON세미나] Spring Cloud를 활용한 MSA](https://www.youtube.com/watch?v=6g1wH97BiuQ&ab_channel=SKplanetTacademy)
- [tedigom.log](https://medium.com/dtevangelist/event-driven-microservice-%EB%9E%80-54b4eaf7cc4a)
- [alwayspr블로그](https://alwayspr.tistory.com/25)