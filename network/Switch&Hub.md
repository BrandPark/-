# 스위치와 허브

우선 여기서 말하는 허브는 `더미 허브`를 의미합니다.

스위치와 허브는 근본적으로 OSI 참조 모델에서 활동하는 영역이 다른데요, 스위치는 2계층, 허브는 1계층 장비입니다.

공통점이라고 한다면 **여러 컴퓨터를 연결하여 회선을 공유**할 수 있다는 것입니다. 그러면 이제 차이점에 대해 알아보겠습니다.

## Fowarding 방식

`허브`는 **입력된 데이터를 복사하여 들어온 포트를 제외한 연결된 모든 포트로 데이터를 전송**합니다. 즉, 아무 논리 없이 전달합니다.

반면에 `스위치`는 **2계층 헤더 정보를 읽고 특정 포트에만 데이터를 전송**(스위칭)합니다. 즉, MAC 주소를 보고 지정해서 전송합니다.

## Duplex

허브와 스위치 모두 양방향 통신이 가능합니다. 하지만 중요한 차이점이 있습니다.

`허브`는 각 포트가 모두 **`Half-Duplex`방식**입니다. 이것은 신호가 양방향으로 흐를 수 있지만, 동시에 양방향은 불가능합니다. 

`스위치`는 각 포트가 모두 **`Full-Duplex`방식**입니다. 이것은 동시에 양방향으로 신호가 전송될 수 있습니다.

(이론적으로 Full-Duplex 방식은 대역폭이 두 배로 늘어나는 효과가 있습니다.)

## BandWidth(대역폭)

`허브`는 **포트별로 대역폭을 분할**합니다. 만약 100Mbps의 대역폭을 가진 외부 회선이 연결된 허브에 4개의 컴퓨터를 연결한다면 각 컴퓨터는 25Mbps의 대역폭을 갖게 됩니다.

`스위치`는 **포트별로 독립된 대역폭**을 갖습니다. 위와 같은 예시에서 각 컴퓨터는 100Mbps의 대역폭을 할당받습니다. 

## Collision

`Collision`이란 **동시에 동일한 매체에게 데이터를 전송하여 발생하는 충돌**을 의미합니다. 

무선(전파) 또는 유선(전기) 신호를 동시에 같은 미디어에게 전송할 때 그것이 충돌하여 신호가 깨지는 것을 의미합니다. 

`허브`는 `Half-Duplex` 방식을 사용하면서 입력된 데이터를 복사하여 모든 포트로 전송한다고 했습니다.

> 만약 허브에 연결된 컴퓨터들이 동시에 데이터를 전송하면 어떻게 될까요?

![image](https://user-images.githubusercontent.com/53790137/151683765-c596b927-f67a-4ee3-94e9-47ecfc21c58f.png)

1. PC1과 PC2에서 PC3에게 데이터를 보내기위해 동시에 Hub에 데이터를 보낸다.
2. PC1의 데이터가 Hub에 먼저 도착하여 다른 모든 포트로 복사, 전송한다.
3. 복사된 PC1의 데이터가 전송중인 PC2의 데이터와 충돌한다.

Half-Duplex 방식이기 때문에 한 포트에서 양방향은 동시에 불가능합니다. 즉, 충돌이 발생합니다.

이렇게 Half-Duplex로 연결되어 충돌이 발생할 수 있는 영역을 `Collision Domain`이라 합니다. 

`스위치`의 모든 포트는 `Full-Duplex`로 동작합니다. 또한 이더넷 헤더(2계층 헤더)를 읽어서 보내기 때문에 Collision이 발생하지 않습니다. 

(이해가 잘 되지 않는다면 다음 글을 참고하시기 바랍니다. [링크](https://community.cisco.com/t5/routing/does-collision-exists-in-switches/td-p/2603901))

## 참고
- [Simplex, Full-Duplex and Half-Duplex Operation](http://www.tcpipguide.com/free/t_SimplexFullDuplexandHalfDuplexOperation-2.htm)
- [Does collision exists in Swiches?](https://community.cisco.com/t5/routing/does-collision-exists-in-switches/td-p/2603901)
- [어째서 스위칭 허브나 스위치를 쓰면 모든 단말이 대역폭을 최대로 사용할 수 있을까요?](https://gigglehd.com/gg/hard/2651795)
