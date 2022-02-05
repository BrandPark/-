# Circuit Breaker

MSA의 장점 중 하나는 장애를 전파시키지 않는다 하였습니다.

서비스간의 네트워크를 통해 통신하는 서비스들은 서로 장애가 전파 될 수 있죠.

서비스A에서 서비스B에게 요청을 보냈을 때 B에서 응답이 지연되거나 예외가 발생하면 A도 장애가 일어날 것입니다.

![image](https://user-images.githubusercontent.com/53790137/152627130-871ec336-0f38-48de-9b64-70847c0d1abe.png)

위의 그림은 의존하는 서비스가 Down이 되어 **의존하는 서비스들이 모두 마비**가 되는 상황입니다.

RestTemplate에 Timeout을 설정한다면 무한정 대기로 인한 전체 서비스 마비는 피하겠지만 결국 예외가 전파되는 것은 같을 것입니다.

**그렇다면 어떻게 장애를 전파시키지 않는 다는 걸까요?**

MSA에서는 `Circuit Breaker`를 사용하여 **장애가 발생하여 사용이 어려운 서비스에게 하는 요청을 빠르게 차단하여 Fail-Fast 응답**을 보내줍니다.

Tomcat과 같은 WAS는 서블릿 스레드의 개수가 한정되어 있고 이러한 스레드가 묶여있는 것은 큰 손실입니다. `Circuit Breaker`는 이러한 자원이 낭비되는 것을 막아줍니다.

정리하면 `Circuit Breaker`는 **장애를 발생시키는 외부 시스템에 대한 연동을 미리 차단함으로써 자신의 시스템을 보호하는 기능**을 합니다.

## 결국 장애 발생이 아닌가요?

`Circuit Breaker`에 의해 요청이 차단되면 Fallback 메서드를 통해 서비스의 기본 행위를 설정할 수 있습니다. 즉, 사용자에게는 장애가 일어나지 않은 것처럼 행동할 수 있다는 것이죠.

예를 들어, 쇼핑몰 페이지의 상단에 사용자의 쇼핑 정보를 분석하여 추천 상품을 나열하는 서비스가 있습니다. 

쇼핑 정보를 분석하는 서비스에서 장애가 발생할 것을 대비하여 정해진 최신 상품들을 보여준다면 사용자는 장애가 일어난지 모를 것입니다.

## Hystrix

이해하기 쉽도록 Neflix oss의 Circuit Breaker 모듈인 `Hystrix`의 순서도입니다.

![image](https://user-images.githubusercontent.com/53790137/152627249-6ec1b1ed-8528-48c7-a1de-f5e07e2e8904.png)

`Circuit Open`이란 Circuit Breaker가 작동했다는 의미이고 요청을 Fail-Fast합니다. 이것을 `Short-circuit`이라 합니다. 

(제 생각에 Circuit Open의 이름은 Short-circuit으로의 길을 열었다는 의미인 것 같습니다.)

`Hystrix`는 **정해진 시간동안 정해진 횟수만큼 메서드 호출이 발생했을 때, 통계를 내어 50% 이상 실패한다면 Circuit Open**을 합니다.

## 참고
- [민수's 기술 블로그](https://alwayspr.tistory.com/26)
- [[토크ON세미나] Spring Cloud를 활용한 MSA 기초](https://www.youtube.com/watch?v=iHHuYGdG_Yk)








