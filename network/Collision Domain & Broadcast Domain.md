# Collision Domain

`Collision`이란 **동시에 같은 수신자에게 데이터를 보내면서 발생하는 충돌**을 의미합니다. 

대표적으로 1계층 공유 매체인 허브나 리피터로 연결된 컴퓨터들은 한번에 하나씩만 데이터를 보낼 수 있고 받을 때는 다 같이 받는 하나의 그룹이 되는데,

만약 허브로 연결된 송신자 A, B가 수신자 C에게 동시에 데이터를 보낸다면 A와 B는 회선을 공유하기 때문에 전기 신호가 겹치게 됩니다.

그렇게 되면 수신자 C가 받는 전기 신호는 읽을 수 없게 되겠죠. 이것을 충돌이라고 합니다. 

`Collision Domain`은 **Collision이 발생할 수 있는 영역**을 의미합니다. 

![image](https://user-images.githubusercontent.com/53790137/151661201-08d66f6c-fb1b-4f5d-a950-a545e544d6d0.png)

- `Hub`는 **모든 포트가 하나의 Collision Domain 입니다.** 1계층 장비로써 1개의 링크를 여러대의 컴퓨터가 공유하면서 Half-Duplex 방식이기 때문입니다.
- `Switch`는 **각 포트가 하나의 Collision Domain 입니다.** 2계층 장비로써 각 포트는 별개의 링크이고 Full-Duplex 방식이기 때문입니다.
- `Router`도 내부의 스위치 기능을 가지고 있습니다. 따라서 스위치와 같은 Collision Domain을 갖습니다. 

# Broadcast Domain
`Broadcast`란 **송신 호스트가 전송한 데이터가 네트워크에 연결된 모든 호스트에 전송되는 방식**을 의미합니다. 

브로드 캐스트 주소를 사용하여 해당 주소 범위에 있는 전체 호스트에게 트래픽을 전달합니다. 전체 네트워크에게 트래픽이 가해지기 때문에 부하가 큰 통신방법이라고 할 수 있습니다. 

`Broadcast Domain`은 **Broadcast가 전달되는 영역**을 의미합니다.

![image](https://user-images.githubusercontent.com/53790137/151661607-333054a2-51c8-41ae-884d-9941e2f71d91.png)

- `Hub`는 **모든 포트가 하나의 Broadcast Domain 입니다.** 
- `switch`도 **모든 포트가 하나의 Broadcast Domain 입니다.**


