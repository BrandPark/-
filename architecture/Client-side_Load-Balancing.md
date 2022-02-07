# Load-Balancing

`Load-Balancing`이란 **서버에 과도한 트래픽이 몰리지 않도록 분산 시키는 것**을 말합니다. S/W로 구현될 수도 있고 H/W로 수행할 수도 있습니다.

## Server-side Load-Balancing

`Server-side Load-Balancing`은 **인터넷과 서버들 사이에서 부하를 분산**시키는 것을 말합니다. 

![image](https://user-images.githubusercontent.com/53790137/152780216-adb6f80c-a490-4590-875c-f839e22aaefd.png)

H/W로는 `L4`나 `L7` 스위치가 있지만 **비용이 비싸 Scale-out이나 이중화가 효율적이지 않다는 단점**이 있습니다. 

S/W로도 구현할 수 있는데 대표적으로 오픈 소스인 `HAProxy`나 `Nginx`가 존재합니다. 

## Client-side Load-Balancing

`Client-side Load-Balancing`은 **클라이언트에 소프트웨어적으로 구현되어 부하를 분산 시키는 것**을 말합니다. 

![image](https://user-images.githubusercontent.com/53790137/152779926-5ddc3502-0a70-4d4e-9446-316674ac86ae.png)

서버 목록이 `Service Registry`에 등록되면 Client가 목록들을 가져와서 직접 로드 밸런싱하여 서버에 요청합니다.

## Client-side Load-Balancing은 왜 등장했을까?

MSA에서 각 서비스들도 시스템의 효율성을 위해 인스턴스가 여러개 있을 것이고 부하분산도 필요합니다. (여기서 클라이언트는 다른 서비스를 호출하는 서비스입니다.)

이때 부하 분산을 서버 사이드로 하게되면 **단일 실패 지점**이 생기게 됩니다. 마이크로서비스 특징상 서비스 끼리 많은 호출이 있기 때문에 **서버 사이드로 구현하면 그곳이 병목 지점이 됩니다.** 

그래서 MSA 서비스 간의 통신에서는 Client-side로 로드 밸런싱을 하자해서 등장한 것이 Client-side Load-Balancing입니다. 

서버 사이드와는 다르게 **각 클라이언트 서비스는 자신의 모든 트래픽을 스스로 로드 밸런싱**하여 단일 병목 지점이 생기지 않습니다. 

대표적으로 `Netflix OSS`의 `Ribbon`이 Client-side Load-Balancer 역할을 할 수 있습니다.

`Spring Cloud`에서 Ribbon을 사용하면 주기적으로 Service Registry에서 목록을 가져와 최신화하기 때문에 요청할 때 Service Registry 서버에 접근할 필요가 없습니다.

## 참고
- [[토크ON세미나] Spring Cloud를 활용한 MSA 기초](https://www.youtube.com/watch?v=iHHuYGdG_Yk)
- [bitsfactory](https://medium.com/bitsfactory/network-load-balancing-what-and-why-d7ff264f957c)








