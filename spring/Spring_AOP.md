# Spring AOP

`Spring AOP`는 프록시 기반으로 AOP를 제공하며, 프록시 방식으로는 `JDK dynamic proxy`와 `CGLIB` 방식을 사용합니다.

Spring Framework에서는 기본적으로 JDK 동적 프록시를 사용하고, Spring Boot에서는 CGLIB을 기본으로 사용한다고 합니다.

## JDK dynamic proxy

`JDK dynamic proxy`는 JDK에 내장되어 있는 Proxy를 사용하며 프록시 패턴을 이용한 방법입니다. 

![image](https://user-images.githubusercontent.com/53790137/154936463-c1c9c7bf-233c-4ed3-b206-df1b8c649a15.png)

그림과 같이 **빈이 인터페이스를 구현한 클래스인 경우에 사용 가능**합니다. 프록시는 내부에 빈을 가지고 있고 같은 인터페이스를 구현합니다. 빈의 메서드가 호출되면 사실은 프록시의 메서드가 호출되고 프록시는 빈을 호출합니다. 이때 추가적인 기능을 덧 붙일 수 있습니다.

정리하면 JDK 동적 프록시는 내부에 빈을 가지고 있고 추가적인 기능을 덧 붙이고 빈에게 위임하는 형식으로 동작합니다.

## CGLIB

`CGLIB`은 Spring이 아니라 타사 라이브러리입니다. Spring은 이것을 인터페이스를 구현하지 않은 빈일 경우에 프록시를 생성하기 위해 사용합니다.

![image](https://user-images.githubusercontent.com/53790137/154937169-dc71caec-8782-4c31-a761-9dac15d088dc.png)

`CGLIB`은 빈을 상속받는 프록시 객체를 생성하고 메서드를 오버라이딩 하여 동작합니다. 

## 언제 프록시 빈으로 교체될까?

Bean의 생명 주기에서 Bean Post Processors에 의해 초기화 콜백이 수행될 때 교체됩니다. 

즉, 빈이 사용 준비가 되기 전에 Proxy로 교체되어 스프링 컨테이너에 들어갑니다.