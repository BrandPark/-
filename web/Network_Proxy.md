# Proxy Server

네트워크에서 `Proxy Server`는 **중간에서 요청을 대신 받는 대체 서버**를 의미합니다. 

Gateway 역할을 수행하는 서버이며 프록시 서버의 위치에 따라 `Reverse Proxy`와 `Forward Proxy`로 나뉩니다. 

둘 모두 프록시 서버로서 **요청과 응답을 캐싱하여 병목을 완화시키는 효과**가 있습니다. 또한 **Gateway로써 단일 액세스 및 제어 지점 역할**을 수행할 수 있습니다.

## Forward Proxy

![image](https://user-images.githubusercontent.com/53790137/152989779-8e4ea336-c134-4d43-8a38-1067063ab244.png)

`Forward Proxy`는 **클라이언트와 인터넷 사이의 Gateway 역할을 수행**합니다. 

할 수 있는 기능은 다음과 같습니다.

1. 클라이언트의 요청과 응답을 캐싱하여 **네트워크 병목 현상을 줄일 수 있습니다.**
2. 내부 네트워크의 단일 액세스 지점으로 **SSL 암호화, 인증 등의 보안 정책을 쉽게 적용하여 보안을 강화**할 수 있습니다.
3. 서버로부터 **클라이언트의 IP를 숨겨줍니다.**(IP 마스킹) 서버는 포워드 프록시 서버의 IP만 알 수 있습니다.

**주로 내부 네트워크의 보안을 강화하는 목적으로 사용**됩니다.

## Reverse Proxy

![image](https://user-images.githubusercontent.com/53790137/152993637-2c1ceaa4-c254-46bf-b2a0-b038a4663acc.png)

`Reverse Proxy`는 **서버와 인터넷 사이의 Gateway 역할을 수행**합니다.

할 수 있는 기능은 다음과 같습니다.

1. 요청과 응답을 캐싱하여 **서버의 부하를 줄일 수 있습니다.**
2. **캐싱을 통해 지리적으로 먼 거리의 서버라도 빠르게 응답**가능하게 해줍니다.(CDN)
2. **서버의 IP를 숨겨줍니다.**(IP 마스킹) 클라이언트는 프록시 서버의 IP만 알 수 있습니다.
3. **로드 밸런싱을 하여 부하를 분산**시킬 수 있습니다.

**캐싱 기능을 통해 지리적으로 먼 거리의 서버의 응답을 빠르게 전달하는 역할과 로드 밸런서**로 사용됩니다.

## 참고
- [Forward Proxy vs. Reverse Proxy Servers](https://www.jscape.com/blog/bid/87783/forward-proxy-vs-reverse-proxy#:~:text=The%20Reverse%20Proxy,proxies%20on%20behalf%20of%20servers.)
- [What is Reverse Proxy Server](https://www.imperva.com/learn/performance/reverse-proxy/)
- [10분 테코톡](https://www.youtube.com/watch?v=YxwYhenZ3BE&ab_channel=%EC%9A%B0%EC%95%84%ED%95%9CTech)
