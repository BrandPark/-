# Spring MVC

스프링 MVC는 Model-View-Controller 디자인 패턴을 따르는 스프링의 모듈입니다.

MVC는 View와 Controller가 분리되어 화면과 백엔드를 독립적으로 개발할 수 있습니다. 하지만 View가 Model을 의존하기 때문에 완벽한 분리는 불가능하다는 단점이 있습니다.

`Spring`은 FrontController라 불리는 `Dispatcher Servlet`이 하나 존재하고 URL에 맞게 핸들러를 매핑하여 사용한다는 특징이 있습니다. 

이것은 요청마다 Servlet을 만들 필요가 없고 개발자는 단순 반복되고 비즈니스 로직과는 거리가 먼 Response header 셋팅과 같은 작업을 하지 않아도 되는 장점이 있습니다.

## Spring MVC 동작 순서

![image](https://user-images.githubusercontent.com/53790137/154939972-1bc7c8f9-0bd3-4922-aa5a-c50dcadf93a2.png)

1. 사용자의 요청을 Dispatcher Servlet이 받습니다.
2. Handler Mapping을 통해 요청 URL에 맞는 핸들러를 받습니다.
3. 빈으로 등록되어있는 HandlerAdapter들을 확인하여 핸들러를 지원하는 Adapter에게 핸들러를 넘기고 handleRequest()를 호출합니다.
4. 핸들러 어댑터는 핸들러의 메서드를 호출합니다.
5. 핸들러는 비즈니스 로직을 수행하고 결과를 Model에 저장한 후 view의 논리 이름을 반환합니다.
6. DispatcherServlet은 ViewResolver에게 view의 논리이름을 전달하고 리졸버는 물리 이름으로 변경하고 렌더링을 수행하는 View객체를 반환합니다.
7. DispatcherServlet은 View객체에게 렌더링을 명령합니다.
8. View객체는 Model을 참조하여 html을 렌더링하고 클라이언트에게 반환합니다. 