# Spring security authentication

모든 보안 프레임워크의 핵심 목표 중 하나는 호출자의 claim을 확인하는 것입니다. Authentication(인증)은 자격 증명 및 호출자의 클레임을 확인하는 프로세스입니다. 

스프링 시큐리티는 서블릿 필터를 사용하여 서버에 대한 요청들을 가로채 요청을 통과시킬지 거부할지 판단합니다.

# Spring Security Authentication flow

![image](https://user-images.githubusercontent.com/53790137/156925782-82e51267-bf2e-4889-854e-76e73b6be093.png)

로그인 요청이라는 가정하에 플로우를 살펴보겠습니다. 

1. 호출자가 HTTP 요청으로 로그인을 요청합니다. 필터 체인의 필터들을 통과하며 `UsernamePasswordAuthenticationFilter`에 도달합니다. (각 요청은 관련 인증 필터에 도달할 때까지 필터를 통과합니다.)
2. 요청의 페이로드에서 username과 password를 추출하여 `UsernamePasswordAuthenticationToken`을 생성합니다. 이 객체는 `Authentication` 인터페이스를 구현한 일종의 인증객체입니다.
3. 인증객체를 `AuthenticationManager`에게 전달하여 인증 작업을 명령합니다.
4. `AuthenticationManager`는 가지고있는 AuthenticationProvider들 중 인증 객체의 인증을 처리할 수 있는 Provider를 찾아 인증 작업을 위임합니다. (AuthenticationManager도 인터페이스이며 구현체로는 대표적으로 `Provider Manager`가 있습니다.)
5. 설정된 Password Encoder를 사용하여 사용자가 입력한 비밀번호를 찾기 위해 암호화합니다.
6. `UserDetailService`의 `loadUserByUsername()`을 호출하여 입력한 username에 매칭되는 계정 정보를 가져오도록 명령합니다.
7. `UserDetailsService`는 DB나 인메모리 등 구현된 코드에 따라 계정 정보를 찾아서 `UserDetails` 타입으로 만듭니다.
8. `UserDetails`는 인터페이스로서 구현체는 `User`가 있습니다. 
9. AuthenticationProvider에게 찾은 계정 정보 객체를 반환합니다. AuthenticationProvider는 계정에 대한 유효성을 체크합니다. (계정이 잠겼는지, 비밀번호가 일치하는지 등) 인증이 완료되었다면 인증객체의 principal에 인증된 사용자의 계정 정보(UserDetails)를 담습니다. 
10. AuthenticationManager에게 인증된 사용자 정보가 담긴 인증객체를 반환합니다.
11. 10번에서 받은 인증객체를 반환합니다.
12. SecurityContextHolder의 `SecurityContext`에 인증객체를 넣습니다. (스프링 시큐리티는 SecurityContextHolder에서 값을 찾으면 인증된 사용자라고 가정합니다.) 응답을 마치면 SecurityContextHolder의 SecurityContext를 비웁니다.