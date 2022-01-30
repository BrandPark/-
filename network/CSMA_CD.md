# CSMA/CD (Carrier Sense Multiple Access with Collision Detection)

`CSMA/CD`란 허브와 같은 **공유 매체를 통해 통신을 시도할 때 Collision을 방지하기 위한 Media Access Control 방법 중 하나**입니다. 

각 구성요소를 개별적으로 나누면 다음과 같습니다.

**Carrier Sense:** 공유 매체에 연결된 다른 노드가 데이터를 수신하거나 송신하고 있는지 확인한 후 아무도 사용하지 않을 때 데이터를 보낸다는 개념입니다. 
**Multiple Access:** 공유 매체에 여러 노드가 접근한다는 의미입니다.
**Collision Detection:** 충돌이 발생하면 이를 감지하고 임의의 시간 후에 전송을 다시 시도합니다. 

종합하면 **공유 매체를 누군가 사용하진 않는지 확인한 후 데이터를 보내고, 충돌이 발생하면 임의의 시간 후에 다시 전송하는 방식**입니다.

## Carrier Sense를 하는데 왜 충돌이 발생할까?

두 노드가 동시에 Carrier Sense를 하면 같은 결과를 받게될 것입니다. 

아무도 통신하고 있지 않다면 두 노드 모두 안전하다고 판단하여 데이터를 보내게 되고 Collision이 발생합니다. 

## 어디에서 사용될까?

주로 `Hub`에서 사용됩니다. 

`Switch`의 경우 모든 포트가 Full-duplex 방식을 사용하고 MAC 주소를 사용하여 통신하기 때문에 Collision이 발생하지 않습니다.

그래서 기본적으로 False로 설정되어 있습니다.
