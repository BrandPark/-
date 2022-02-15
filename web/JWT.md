# JWT

`JWT(Json Web Token)`은 **JSON으로 데이터를 담아 인코딩을 통해 쉽게 전달 가능하고 자체적으로 메시지의 변조를 체크할 수 있는 Signature를 가진 토큰**입니다. 

서버와 클라이언트 또는 서버와 서버 사이의 데이터 전송이나 인증, 인가에 사용됩니다. 

## 일반적인 API Access Token

일반 적인 API Token의 인증 방식을 그림으로 표현하면 다음과 같습니다. 

![image](https://user-images.githubusercontent.com/53790137/153832266-2d7e5cd1-6579-40d1-b34e-695ae8a6f254.png)

1. API Token을 발급해주는 서버에 **로그인하여 토큰을 요청**합니다.
2. API 토큰 관리 서버는 앞으로 토큰을 사용할 대상이 누군이지 매핑해야 하기 때문에 **DB에 토큰과 유저 정보를 저장**합니다.
3. **생성된 Access Token을 클라이언트에게 전달**합니다.
4. 클라이언트는 API 서버에게 **발급받은 Access Token을 통해 인증을 요청**하고 서버는 **DB를 확인**하여 토큰이 있는지, 만료되진 않았는지 확인하여 인증 처리를 진행합니다.

Stateless한 서버를 만들어 확장성을 높이기 위해 서버에서 세션에 클라이언트 정보를 저장하지 않기 위해 나온 것이 `Token`입니다.

하지만 Access Token이 유효한지, 이 사람의 토큰이 맞는지, 어떤 권한을 가지고 있는지 등을 확인하기 위해서는 **서버가 DB를 조회하여 확인**해야 합니다. 

이것은 서버의 확장성을 얻은 대신 **DB의 부하를 증가시켜 병목 지점**이 DB에 생기게 됩니다. 차라리 Memcached나 Redis와 같은 캐시 서버를 사용하여 세션을 클러스터링 하는 것이 더 좋을 수 있습니다.

## JWT 장점

JWT의 가장 큰 장점이자 특징은 다음과 같습니다.

- Token이 권한과 만료시간과 같은 **데이터를 직접 가지고 있습니다.**
- Token만 확인해도 **중간에 변조되었는지 알 수 있습니다.** 그래서 서버를 제외하고는 읽기만 가능합니다.
- 단순히 인코딩되어 있기 때문에 파싱과 사용이 쉽습니다.

서버는 Token에 데이터가 모두 들어있기 때문에 DB에 저장할 필요가 없습니다. 즉, 완전하게 `Statless` 한 서버를 구축하는 것이 가능해집니다. 

**서버의 확장성과 더불어 DB를 조회 할 필요도 없기 때문에 자원을 아끼고 레이턴시도 줄일 수 있습니다.**

## JWT의 구조

`JWT`는 `Header`와 `Payload`, `Signature` 3부분으로 구성됩니다. 

#### Header

![image](https://user-images.githubusercontent.com/53790137/153837887-e9af4e79-20ef-4e1b-b2c2-54446e58f6fb.png)

JSON으로 이루어져 있으며 Signature에서 사용 될 해싱 알고리즘과 **토큰의 타입과 같은 메타 정보**를 담습니다.

#### Payload

![image](https://user-images.githubusercontent.com/53790137/153838047-ab4ea715-87ef-4dcf-b3a3-1395bb0acccb.png)

**전달할 데이터**가 들어가는 부분입니다. `Claim`도 여기에 들어갑니다. 클레임이란 사용자에 대한 정보나 발급자, 만료시간 및 데이터에 대한 설명입니다.

#### Signature

![image](https://user-images.githubusercontent.com/53790137/153839121-0fd49877-7490-4272-aef1-bd0cd988997e.png)

가장 중요한 부분입니다. **토큰이 변조되었는지 알 수 있는 부분**입니다.

헤더와 페이로드를 각각 `Base64`로 인코딩하고 서버만 알 수 있는 `Secret`을 첨가하여 헤더에 쓰여있는 해싱 알고리즘으로 해싱합니다. `Secret`은 해싱 알고리즘이 뚫리는 것을 막는 일종의 `Salt` 역할을 하기 때문에 절대 외부로 알려지면 안됩니다.

`서버`는 JWT를 받으면 **직접 위와 같은 작업을 통해 직접 Signature를 만들어 토큰의 Signature과 비교**하여 변조되었는지 확인합니다.

해싱 알고리즘만 뚫리지 않는다면 토큰이 변조되지 않았다는 것을 보장할 수 있습니다.

![image](https://user-images.githubusercontent.com/53790137/153840466-a0d529e4-524c-4577-a028-8b7a9ef37ef2.png)

이렇게 만들어진 JWT의 모습입니다. 각 부분은 Base64로 인코딩되고 `.`으로 구분되어 전달하기 편한 문자열이 됩니다.  

## JWT 단점

JWT는 장점만큼 단점도 명확합니다. 

1. <ins>**쉽게 읽을 수 있기 때문에 민감한 정보는 주의**</ins>해야합니다. `JWE`를 사용하여 토큰 자체나 민감한 정보를 암호화하여 전송 할 수 있지만 그 만큼 서버가 토큰을 읽는데 오래 걸립니다.
2. <ins>**한 번 발급 된 토큰은 수정하거나 폐기가 불가능**</ins>합니다. 비밀번호가 바뀌거나 탈취를 당해도 만료 기간이 끝날 때 까지 서버가 폐기할 수 없습니다. 그래서 만료 기간을 명시적으로 짧게 두어야 합니다. 
3. <ins>**토큰을 탈취당해도 알 수 없습니다.**</ins> 폐기도 불가능 하기 때문에 만료 기간을 짧게 두어 도난 당할 확률을 낮추는 방법 말고는 없습니다. 
4. <ins>**만료 기간을 짧게 둘 경우 잦은 재인증으로 UX가 떨어집니다.**</ins>
5. <ins>**전송할 데이터가 많아질수록 문자열도 길어져 대역폭 낭비가 발생**</ins>합니다. 토큰의 특성 상 요청마다 헤더에 붙어서 전송되기 때문에 토큰이 무거워지는 것은 되도록 피해야합니다.

정리하면 JWT는 서버가 제어할 수 없기 때문에 탈취당해도 알 수 없고 폐기도 불가능해서 만료 기간을 짧게 설정해서 리스크를 줄이는 수 밖에 없습니다. 

그에 따라 잦은 인증으로 사용성이 떨어진다는 단점이 추가적으로 생기게 됩니다.

## Refresh Token

`Refresh Token`은 **Access Token의 만료 기간이 끝나도 다시 인증 절차를 거치지 않고 Access Token을 발급 받게 해주는 토큰**입니다.

`Refresh Token`의 유효 기간을 길게 설정하고 이것으로 JWT의 짧은 만료 기간으로 잦은 인증이 발생하는 문제점을 해결하여 **로그인을 유지하는 것이 가능하게 합니다.**

![image](https://user-images.githubusercontent.com/53790137/153846029-ae3170a2-7940-4370-b2a2-07561716779e.png)

JWT와 Refresh Token을 같이 사용하는 Flow입니다. 토큰을 발급 받고 난 후인 보라색 번호만 보겠습니다.

1. JWT를 Header에 넣어 서버에게 전송합니다.
2. JWT의 만료 기간을 보고 만료 된 토큰임을 알리는 Error가 발생합니다.
3. 인증이 실패되었다는 401상태와 함께 토큰이 만료되었다는 메시지를 받습니다.
4. `Refresh Token`을 전송하여 JWT 재발급 요청을 합니다.
5. `Refresh Token`을 검증합니다.
6. 새로 발급된 JWT를 전달합니다.
7. 새 JWT로 다시 요청합니다.

## Refresh Token 탈취

`Refresh Token`은 보통 만료 기간을 길게 설정합니다. 이것을 탈취당한다면 해커는 만료 기간이 끝날 때 까지 계속해서 JWT를 발급받을 수 있게 되는 큰 보안 문제가 발생합니다.

따라서 <ins>**서버는 `Refresh Token`이 탈취 당하는 상황에 대한 대책을 마련해 놓아야합니다.**</ins>

[`Stack Overflow`의 답변](https://stackoverflow.com/questions/32060478/is-a-refresh-token-really-necessary-when-using-jwt-token-authentication)은 다음과 같습니다.

1. `Refresh Token`과 1대1 매핑되는 `Access Token`을 DB에 저장합니다.
2. 해커가 `Refresh Token`을 탈취하여 `Access Token`을 새로 발급해달라고 요청(Refresh 요청)합니다.
3. 서버는 Refresh 요청이 오면 DB를 확인합니다.
4. `Access Token`의 만료 기간이 아직 끝나지 않았는데 Refresh 요청이 오면 탈취 당했다고 판단하고 Refresh Token을 무효화 시킵니다.
5. 정상 사용자는 다시 로그인을 해야합니다.

`Refresh Token Rotation`이라는 다른 방법도 존재합니다. 이 방법은 Refresh 요청이 오면 Access Token(JWT)만 새로 발급하는 것이 아니라 Refresh Token도 새로 발급해 주는 방법입니다. 즉, **Refresh Token의 수명 주기를 Access Token의 수명 주기에 맞춰 짧게 사용하도록 만드는 것**입니다.

논리는 다음과 같습니다.

1. 유저가 `Refresh_Token_1` 과 `Access_Token_1`을 가지고 있습니다. 
2. 해커가 `Refresh_Token_1`을 탈취하는데 성공합니다.
3. 유저가 `Refresh_Token_1`을 사용하여 새 `Refresh_Token_2`와 `Access_Token_2`를 발급받습니다.
4. 해커가 `Refresh_Token_1`을 사용하여 액세스 토큰을 얻으려고 시도합니다. 서버는 DB를 확인하여 재사용 중인 토큰임을 인식하고 토큰들을 무효화 시킵니다. 
5. 해커는 액세스 거부 응답을 받고 유저 또한 인증을 다시 하여 새로 발급받아야 합니다. 

자세한 내용은 [Auth0 docs]](https://auth0.com/docs/secure/tokens/refresh-tokens/refresh-token-rotation)를 참고해보시면 좋을 것 같습니다.

이 방법은 3번에서 설사 해커가 먼저 토큰을 탈취했다고 하더라도 사용자가 refresh 할 때까지만 사용이 가능합니다.  추가적으로 사용자에게 알림이 가도록 만든다면 빠르게 조취를 취하게끔 할 수도 있습니다.

## 추가적인 보안 처리

위의 방법들은 이미 탈취되었을 때 취할 수 있는 조치 방법들입니다. 보안에서 가장 좋은 방법은 아예 일어나지 않도록 예방하는 것입니다. 

#### HTTPS 를 사용합니다.

네트워크는 데이터가 쉽게 노출되는 공간입니다. 노출되도 읽을 수 없도록 암호화 하여 전달해야 할 것입니다. HTTPS를 사용하여 데이터를 암호화하여 전달하여 보안을 강화할 수 있습니다.

#### Http-Only와 Secure 옵션

토큰은 보통 쿠키로 많이 저장하여 사용합니다. `Http only`옵션과 `Secure`옵션을 on으로 설정하여 Script 로 쿠키에 접근하지 못하도록 막고 HTTPS를 사용할 경우에만 데이터를 전송하도록 제한하여 보안을 강화할 수 있습니다. Http-Only 옵션은 필수라고 볼 수 있습니다.

## 참고
- [REST API 보안 및 인증 방식](https://dongwooklee96.github.io/post/2021/03/28/rest-api-%EB%B3%B4%EC%95%88-%EB%B0%8F-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D/)
- [StackOverflow](https://stackoverflow.com/questions/57650692/where-to-store-the-refresh-token-on-the-client)
- [JWT.io](https://jwt.io/)
- [Refresh token with JWT authentication in Node.js](https://www.izertis.com/en/-/refresh-token-with-jwt-authentication-in-node-js)
- [Refresh Token Rotation](https://auth0.com/docs/secure/tokens/refresh-tokens/refresh-token-rotation)