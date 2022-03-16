# Http Method

`Http Method`는 Http 메시지의 처리방법을 서버에게 알려주는 기능을 합니다. 

`Http Method`들은 `Idempotent(멱등성)`와 `Safety(안전성)` 등의 제약조건들을 가지고 있고 사용 방법이 정해져있습니다.

이 규칙들은 강제되진 않지만 원활한 통신을 위해 지키는 것이 좋습니다.

![image](https://user-images.githubusercontent.com/53790137/158578450-08cb1569-c748-44ee-be77-2dbc3b646b9d.png)

# Safety(안전성)

요청을 해도 리소스의 상태 변경이 없는 Http Method를 Safe하다고 합니다. `GET`, `HEAD`, `OPTIONS`, `TRACE`를 제외하곤 모두 안전하지 않습니다.

# Idempotent(멱등성)

같은 요청을 한 번 보내든, 여러 번 보내든 상관없이 같은 Side-effect를 발생시키는 것을 Idempotent하다고 합니다. `Safe`한 Method는 `Idempotent` 하지만 그 역은 아닙니다.

# Example
- **GET & HEAD & OPTIONS & TRACE**

  `GET`과 `HEAD`은 데이터를 조회하기 위한 Method입니다. 따라서 검색 이외의 조치를 취하지 않아야 한다는 규칙이 확립되었기 때문에 Safety하고 Idempotent 합니다. `OPTIONS`와 `TRACE` 또한 Side-effect를 갖지 않으므로 본질적으로 Safety하고 Idempotent 합니다.

- **POST**
  
  `POST`는 요청 URI에 의해 식별된 리소스의 새 하위 항목으로 Origin 서버가 추가하도록 요청하는데 사용합니다. 쉽게 말해 리소스를 새로 생성할 때 사용합니다. 첫 번째 요청과 두 번째 요청으로 생성된 리소스는 서로 다릅니다. 즉, side-effect가 다르기 때문에 Idempotent하지 않습니다.

- **DELETE**
  
  `DELETE`는 요청 URI에 의해 식별된 리소스를 Origin 서버에 삭제 요청할 때 사용합니다. 한 번 요청하든 여러 번 요청하든 항상 같은 Side-effect를 발생시킵니다. 
  
  이해를 돕기위해 예를 들자면 삭제 자원이 있는 경우 삭제하고 200 응답, 없는 경우 400 응답을 하는 서버가 있습니다. 여기서 Side-effect는 리소스가 있는 경우 해당 리소스 삭제, 없는 경우 무반응입니다. 이것은 요청의 횟수와 상관없이 항상 같습니다. 따라서 DELETE는 Idempotent합니다. 

- **PUT**
  
  `PUT`은 요청 URI에 의해 식별된 리소스를 교체하도록 요청할 때 사용합니다. 만약 없다면 새로 생성합니다. Side-effect는 PUT으로 요청한 리소스와 일치하는 리소스가 서버에 생긴다는 것입니다. 내용이 같은 첫 번째 요청과 두 번째 요청은 모두 같은 Side-effect를 발생시킵니다. 

  `POST`와의 근본적인 차이점은 요청 URI에 있습니다. `PUT`은 요청에 포함된 엔티티를 식별하지만 `POST`는 요청에 포함된 엔티티를 처리할 리소스를 식별합니다. 쉽게 말하면 요청 URI로 엔티티를 식별할 수 있는 것이 PUT, 엔티티를 식별하는 것이 아니라 엔티티를 처리 할(생성 할) 리소스를 식별하는 것이 POST입니다.

  ```
  POST, /members  HTTP/1.1
  PUT, /members/1 HTTP/1.1
  ```

- **PATCH**
  
  `PATCH`는 리소스의 일부를 변경할 때 사용합니다. 특정 엔티티를 전부 덮어쓰는 PUT에 비해 좀 더 범용적인 수정 작업에 사용되기 때문에 Idempotent할 수도 있고 Non-idempotent할 수도 있습니다. 예를 들어 다음과 같이 사용할 수 있습니다.

  - 특정 엔티티의 일부 데이터 수정
  - 데이터를 전송하여 서버의 배열에 추가하기 (ex. {"op":"add", "path":"/-", "value":"foo"})

  첫 번째의 경우 PUT과 마찬가지로 Idempotent 하지만 두 번째의 경우 서버에 존재하는 배열은 첫 요청은 `{"foo"}` 두 번 째 요청은 `{"foo", "foo"}`가 되므로 Idempotent 하지 않습니다. 