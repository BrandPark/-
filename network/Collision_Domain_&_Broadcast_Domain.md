# Collision Domain

`Collision`이란 **동시에 같은 수신자에게 데이터를 보내면서 발생하는 충돌**을 의미합니다. 

대표적으로 1계층 공유 매체인 허브나 리피터로 연결된 컴퓨터들은 한번에 하나씩만 데이터를 보낼 수 있고 받을 때는 다 같이 받는 하나의 그룹이 되는데,

만약 허브로 연결된 송신자 A, B가 수신자 C에게 동시에 데이터를 보낸다면 A와 B는 회선을 공유하기 때문에 전기 신호가 겹치게 됩니다.

그렇게 되면 수신자 C가 받는 전기 신호는 읽을 수 없게 되겠죠. 이것을 충돌이라고 합니다. 

`Collision Domain`은 **Collision이 발생할 수 있는 영역**을 의미합니다. 

![image](https://user-images.githubusercontent.com/53790137/151661201-08d66f6c-fb1b-4f5d-a950-a545e544d6d0.png)

### Hub

`Hub`는 **모든 포트가 하나의 Collision Domain 입니다.** 

허브는 1계층 장비로써 입력된 데이터는 논리없이 모든 포트로 전달되며 Half-Duplex 방식을 사용합니다.

연결된 모든 컴퓨터와 마치 하나의 큰 데이터 버스처럼 동작하기 때문에 Collision Domain을 공유합니다.

### Bridge

`Bridge`는 **Collision Domain을 나눕니다.**

2계층 장비로써 프레임의 MAC주소를 읽고 전달합니다. 또한 전달할 네트워크에 CSMA/CD가 동작하도록 하기 때문에 Collision Domain을 나누는 효과가 있습니다. 

### Switch & Router

`Switch`는 **각 포트가 하나의 Collision Domain 입니다.** 

2계층 장비로써 입력된 데이터를 MAC 주소를 통해 포트를 지정하여 포워딩하며 모든 포트는  Full-Duplex 방식을 사용합니다.

따라서 스위치에 연결된 모든 링크는 독립적인 링크이기 때문에 Collision Domain이 포트별로 나뉩니다.

`Router`도 기본적으로 스위치 기능을 가지고 있습니다. 따라서 스위치와 같은 논리로 Collision Domain을 갖습니다. 

# Broadcast Domain

`Broadcast`란 **송신 호스트가 전송한 데이터가 네트워크에 연결된 모든 호스트에 전송되는 방식**을 의미합니다. (One-to-All)

LAN에서 스위치가 대상 **MAC 주소가 FF-FF-FF-FF-FF-FF**인 프레임을 수신하면 수신한 인터페이스를 제외한 모든 인터페이스로 프레임을 복사하여 보냅니다. 

네트워크를 통해 전달되었다면 브로드 캐스트 IP 주소를 사용하여 해당 주소 범위에 있는 전체 호스트에게 브로드 캐스트합니다.

브로드캐스트는 **전체 네트워크에게 트래픽이 복사되어 가해지기 때문에 부하가 큰 통신방법**이라고 할 수 있습니다. 

`Broadcast Domain`은 **Broadcast가 전달되는 영역**을 의미합니다.

![image](https://user-images.githubusercontent.com/53790137/151661607-333054a2-51c8-41ae-884d-9941e2f71d91.png)

### Hub

`Hub`는 **모든 포트가 하나의 Broadcast Domain 입니다.** 

Hub는 입력된 데이터를 다른 모든 포트에 Flooding하기 때문에 하나의 Broadcast Domain을 형성합니다.

## Switch

`Switch`도 **모든 포트가 하나의 Broadcast Domain 입니다.**

Switch는 MAC주소가 FF-FF-FF-FF-FF-FF 인것을 확인하고 수신 포트를 제외한 모든 포트에 Flooding합니다. 즉, 헤더만 확인할 뿐 결과는 Hub와 같습니다.

## Router

`Router`는 브로드캐스트 프레임을 전달받으면 Flooding하지 않고 Decapsulation하여 IP 패킷내에있는 3계층 정보에 따라 동작합니다.

즉, Router는 **Broadcast Domain을 물리적으로 나누는 효과**가 있습니다. 



