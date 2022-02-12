# Service Registry

MSA에서 Client-side Load-Balancing을 수행하기 위해서는 `Service Registry`가 필요합니다. (Service Discovery라고도 부릅니다.)

`Service Registry`는 **서비스를 탐색, 등록하여 목록을 저장하고있는 일종의 데이터베이스**입니다.

서비스 레지스트리가 없다면 로드밸런서에 직접 서버 목록을 작성해야 합니다. 이것은 Cloude Native하지 않습니다. 

![image](https://user-images.githubusercontent.com/53790137/152780216-adb6f80c-a490-4590-875c-f839e22aaefd.png)

`Service Registry`는 서비스가 시작되면 그것을 등록하고, 서비스가 중지되면 상태를 변경(Down)하거나 삭제하는 것을 자동으로 해줍니다.

이로써 Client-side Load-Balancer는 서버 서비스와 완전히 분리되어 동작할 수 있게됩니다. 

대표적으로 `Netflix Eureka`가 있습니다. Spring Cloud에서 공식 지원합니다.

## 동작

1. 각 서비스 서버는 실행될 때 Service Registry에 IP, Port 등의 서버 정보를 저장합니다.(Register)
2. 등록된 서비스 서버 목록이 살아있는지 주기적으로 `Heart Beat`신호를 보내 살아있는지 체크하고 응답이 없으면 지웁니다.
3. LB는 주기적으로 Service 목록을 가져와 메모리에 캐싱하여 사용합니다. 


`Service Registry`의 주 목적은 **살아있는 서버들의 IP와 Port를 가지고 유지**하는 것입니다. 

**DNS 서버를 거치지 않아도 되기 때문에 성능상의 이점**도 있습니다. 


## 참고
- [[토크ON세미나] Spring Cloud를 활용한 MSA 기초](https://www.youtube.com/watch?v=iHHuYGdG_Yk)
- [bitsfactory](https://medium.com/bitsfactory/network-load-balancing-what-and-why-d7ff264f957c)








