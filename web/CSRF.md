# CSRF(Cross-Site Request Forgery)

`CSRF`란 **해커가 악의적으로 만든 URL을 의도적으로 사용자가 접속하게 만들어서, 마치 사용자가 요청한 것처럼 위조**하는 것을 의미합니다.

`CSRF` 공격의 핵심은 **인증된 사용자에 대한 웹 사이트의 신뢰를 악용**하는 것입니다. 

## 공격 방법

`CSRF` 공격을 성공시키기 위해서는 다음의 조건이 필요합니다.

1. `해커`는 속이고자 하는 `웹 사이트A`에서 원하는 기능의 URL과 파라미터를 파악해야 합니다.
2. `피해자`는 `웹 사이트A`와 Session이 Active 인 상태로 해커가 만든 URL을 열어야 합니다.
3. `웹 사이트A`는 요청이 올바른 경로로 들어온 요청인지 위조되었는지 구분할 수 없어야 합니다.

```
예를 들어 `Bob`이 samplebank.com에 온라인 뱅킹 계좌를 가지고 있다고 가정해 보겠습니다. 

그는 정기적으로 이 사이트를 방문하여 친구 `Alice`와 거래를 합니다.

`해커`는 samplebank.com이 CSRF 공격에 취약하다는 사실을 알고 있습니다. 

`해커`는 Bob의 계정에서 5000만원을 자신에게 이체하는 것을 목표로 합니다.
```

samplebank.com이 송금 기능을 GET 메서드를 사용하여 구축했다고 가정하겠습니다.

`Bob`이 `Alice`(계정 번호 213367)에게 10,000원을 이체하는 요청은 다음과 같을 수 있습니다.
```
	HTTP/1.1 GET https://samplebank.com/onlinebanking/transfer?amount=10000&accountNumber=213367
	-- Bob이 Alice에게 만원을 이체하는 요청
```

`해커`는 CSRF 공격 조건의 **첫 번째 조건**인 URL을 만들어 Bob이 자신의 계정 425654로 5000만원을 이체하는 URL을 만들어야 합니다.
```
	HTTP/1.1 GET https://samplebank.com/onlinebanking/transfer?amount=50000000&accountNumber=425654
	-- Bob이 해커(425654)에게 5000만원을 이체하는 요청
```

다음으로 **두 번째 조건**인 피해자가 이 URL을 열도록 해야 합니다. Bob은 samplebank 웹 사이트를 자주 이용해서 세션이 활성화된 상태라고 가정하겠습니다.

**URL을 열도록 하는 방법**은 여러가지가 있습니다.
1. 페이지에 HTML 이미지 요소에 URL을 넣습니다.
2. 웹 사이트에 로그인한 상태에서 사용자가 자주 액세스하는 페이지에 악성 URL을 배치합니다.
3. 이메일을 통해 악성 URL을 전송합니다.

`해커`는 1번과 3번을 조합한 방법으로 Bob이 URL을 열도록 할 생각입니다. 다음은 해커가 만든 HTML 양식의 예입니다.
```
	<img src="https://samplebank.com/onlinebanking/transfer?amount=50000000&accountNumber=425654" width="0" height="0">
```

`해커`가 이 양식을 **Bob에게 메일로** 보낸다고 가정해 보겠습니다.

`Bob`이 메일을 여는 순간 **브라우저는 사람의 개입 없이 이 URL을 자동으로 엽니다.** 결과적으로 Bob의 허가 없이 samplebank에 Bob이 요청한 것 처럼 악성 요청이 전송됩니다.

## CSRF 방지 방법

CSRF 공격을 막기 위해 애플리케이션은 HTTP 요청이 **자신의 UI를 통해 합법적으로 생성된 요청인지 확인**할 수 있어야 합니다. 

이를 달성하는 가장 좋은 방법은 `CSRF 토큰`을 사용하는 것입니다. CSRF 토큰을 적용하는 방법은 쉽습니다.

1. CSRF 토큰은 추측하기 어렵게 범위가 큰 임의의 난수여야 합니다.
2. 애플리케이션은 각 사용자 세션에 고유한 CSRF 토큰을 할당해야 합니다.
3. 애플리케이션은 UI의 Form에 CSRF 토큰을 숨겨서 사용자가 요청할 때 CSRF 토큰을 같이 보내도록 해야합니다.
4. 애플리케이션은 중요한 작업의 경우 CSRF 토큰을 확인하여 위조된 요청인지 확인합니다.

## 참고

- [Synopsys](https://www.synopsys.com/glossary/what-is-csrf.html)




