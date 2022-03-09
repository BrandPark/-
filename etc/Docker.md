# Docker

`Docker`는 Container 기술을 사용하여 빠르게 애플리케이션과 실행 환경을 구축, 배포할 수 있는 소프트웨어 플랫폼입니다. 

도커 파일로 애플리케이션의 인프라를 코드로 관리(Infrastructure as Code) 할 수 있습니다. 

# VM vs Container

![image](https://user-images.githubusercontent.com/53790137/157370205-f2036753-71ba-40e7-acaf-8fa992f45449.png)

`VM`은 **하드웨어 수준으로 프로세스를 격리(가상화)**하는 기술입니다. 각 VM은 별도의 Guest OS를 생성하기 때문에 실행되기까지 속도가 느리고 공유되는 것 없이 완전한 격리가 이루어집니다.

`Container`는 **OS 수준으로 프로세스를 격리(가상화)**하는 기술입니다. 컨테이너는 Host OS를 공유하기 때문에 가상 머신(VM)보다 적은 리소스를 사용하고 빠르게 부팅이 가능합니다.

# 참고
- https://www.youtube.com/watch?v=LXJhA3VWXFA&ab_channel=%EB%93%9C%EB%A6%BC%EC%BD%94%EB%94%A9by%EC%97%98%EB%A6%AC
- https://geekflare.com/docker-vs-virtual-machine/