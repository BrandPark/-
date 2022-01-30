# DHCP(Dynamic Host Configuration Protocol)

`DHCP`는 **IP 주소, 서브넷 마스크, DNS 서버 IP 주소 등의 네트워크 정보를 할당해주는 프로토콜**입니다.

DHCP를 사용하면 사용자는 네트워크 정보를 직접 설정할 필요가 없어 네트워크 관리의 편리성을 제공합니다.

## IP 주소 할당 절차

![image](https://user-images.githubusercontent.com/53790137/151689676-5a15fbe7-0e72-4112-9026-1be1341c6a9f.png)

#### 1. DHCP Discover

**단말이 DHCP 서버를 찾는 단계**입니다. DHCP 서버를 찾기 위해 Discover 메시지를 **Broadcasting**합니다.

#### 2. DHCP Offer

**DHCP 서버가 자신을 알리며 IP 주소를 예약하고 임대를 제안하는 단계**입니다.

DHCPOFFER 메시지에는 DHCP서버의 IP, 예약한 IP, 서브넷 마스크, 임대 기간 등이 들어있습니다. 메시지는 **Broadcasting**됩니다.

#### 3. DHCP Request

**Client가 제안된 메시지 중 마음에 드는 것을 골라 요청하는 단계**입니다.

클라이언트는 마음에 드는 제안을 한 DHCP 서버의 IP 주소를 지정하는 요청을 **Broadcasting**합니다.

브로드캐스트를 받은 DHCP 서버들 중 선택되지 않은 서버는 예약한 IP를 취소합니다.

#### 4. DHCP Ack

**서버가 IP 주소를 할당하고 DHCP 데이터 저장소에 정보를 저장하는 단계**입니다.

서버는 승인 메시지(ACK)를 클라이언트에게 보냅니다. 승인 메시지에는 네트워크 구성 요소(IP, 서브넷 마스크, 임대 기간, DNS IP, Gateway 등)이 들어있습니다.

## 주소 임대기간 연장 절차

클라이언트는 임대 시간을 모니터링하면서 **설정된 시간이 경과하면 임대 시간을 늘리기위한 새 메시지를 DHCP 서버에게 보냅니다.**

![image](https://user-images.githubusercontent.com/53790137/151690191-bf318282-a511-4e59-996b-577a23ce59dc.png)

#### 1. DHCP Request

클라이언트는 임대시간 연장 요청 메시지를 **Unicasting**으로 전송합니다. 일정 시간 응답이 없으면 다른 DHCP 서버가 연장 요청을 처리하도록 **Broadcasting**합니다.

#### 2. DHCP Ack

DHCP 서버는 현재 로컬 임대 정책을 계속 준수한다면 임대시간을 연장하고 클라이언트에게 응답해줍니다.

## IP 주소 반납 절차

PC를 로그오프하거나 ipconfig /release를 하게 되면 단말은 할당된 IP 주소를 DHCP 서버에게 반환합니다.

![image](https://user-images.githubusercontent.com/53790137/151690335-0ded5553-d839-4a7c-b34f-695dc26d2850.png)

#### 1. DHCP Release

DHCP Release 메시지를 **Unicasting**으로 서버에게 전달하여 IP 주소를 반환합니다. 
