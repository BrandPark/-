# Spring Security

`Spring Security`는 **Spring 기반 애플리케이션을 보호하기 위한 사실상의 표준**입니다. 

Spring Security는 다음 네 가지 핵심 개념을 따릅니다.

1. Authentication(인증)
2. Authorization(인가)
3. Password Encoder
4. Servlet Filters

### 1. Authentication : 인증

`Authentication(인증)`은 **사용자가 누구인지 확인**하는 것입니다. 애플리케이션은 인증된 사용자에게 자격 증명을 제공하고 매번 그것을 확인하여 누구인지 식별합니다. 이때 인증을 시도하는 사용자를 `Principal(주체)`라고 합니다. 

### 2. Authorization : 인가

`Authorization(인가)`는 **사용자의 권한을 확인**하는 것입니다. 간단한 응용 프로그램의 경우 인증으로 충분할 수 있지만 사용자별로 권한을 달리하여 자원의 접근을 제한하고 싶다면 Authorization이 필요합니다. 

### 3. Password Encoder

비밀번호가 안전하고 해킹하기 어렵게 하여 저장하는 것도 보안 프레임워크의 주요 목표입니다. Spring Security의 `Password Encoder` 인터페이스는 비밀번호에 대한 **단방향 변환을 수행**합니다. 즉, **비밀번호를 해독할 수 없습니다.**

Spring Security는 다음과 같이 여러 가지 Password Encoder를 제공합니다. 

- BCryptPasswordEncoder
- Argon2PasswordEncoder
- Pbkdf2PasswordEncoder
- SCryptPasswordEncoder

### 4. Servlet Filters

Spring Security는 Java Servlet filter를 사용하여 웹 애플리케이션에 대한 보안 검사를 수행합니다.

# Spring Security의 장점

- 독립적인 운용을 지향
- Servlet API 통합
- 인증 및 권한 부여 모두에 대한 포괄적이고 확장 가능한 지원
- 스프링 MVC와의 통합 가능
- Session fixation, click jacking, CSRF와 같은 공격에 대한 보호

