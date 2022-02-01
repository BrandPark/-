# TCP

`TCP(Transmission Protocol)`은 **연결지향형 전송제어 프로토콜**입니다.

4계층(Transport Layer)에 존재하는 표준 프로토콜로써 헤더에 Port번호도 가지고 있고 이름 그대로 전송을 제어하는 기능도 가지고 있습니다.

## 3 way-handshake (연결)

TCP가 연결지향형 프로토콜인 이유는 **논리적인 연결 과정**을 거치기 때문입니다. 

데이터를 보내기전에 3번 연결을 주고 받음으로써 논리적인 연결을 보장하는 것이 `3 wa-handshake`입니다.

![image](https://user-images.githubusercontent.com/53790137/151968918-5179698b-1ac2-462d-ad41-77d7c9b05f06.png)

1. `클라이언트`는 서버에게 연결이 가능한지 `SYN(m)` 메시지를 보냅니다. 
2. `서버`는 SYN(m)의 응답 메시지 ACK(m+1)과 함께 연결이 가능한지 `클라이언트`에게 SYN(n) 메시지를 같이 보냅니다.
3. `클라이언트`는 서버의 SYN(n) 메시지를 받았다는 응답 메시지 ACK(n+1)을 보내면서 논리적 연결이 형성됩니다.

## 4 way-handshake (연결해제)





