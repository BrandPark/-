# Branch

`Branch`는 깃의 꽃이라고 부릅니다. 깃의 브랜치는 **커밋에 대한 포인터**만 가지고 있기 때문에 매우 가볍습니다.

그래서 순식간에 브랜치를 새로 만들고 브랜치 사이를 이동하는 것이 가능합니다. 

## Branch 파일 내용

![image](https://user-images.githubusercontent.com/53790137/152642840-feff3f26-7e5f-4cdb-8e68-b00d1bd69b25.png)

실제로 `.git` 폴더의 브랜치 파일을 찾아 열어보면 위와 같이 **커밋의 해시값만 들어있습니다.** 

`Head`, `Tag` 도 브랜치와 같이 포인터로써 역할을 합니다.

이렇게 해시값만 가진 포인터이고 스냅샷이라는 특별한 저장구조로 되어 있기 때문에 **브랜치 전환이 빠르고 용량도 적습니다.**

## $ git branch [branch-name]

현재 `master`브랜치 에서 `$ git branch testing`을 입력하면 어떻게 될까요?

![image](https://user-images.githubusercontent.com/53790137/152642576-63783706-7a30-4008-81e2-fca6998e5fc7.png)

그림과 같이 master와 같은 커밋을 가리키게 됩니다. 그러면 의문이 생길 것입니다.

**지금 작업 중인 브랜치가 무엇인지 Git은 어떻게 파악할까?**

다른 버전 관리 시스템과는 달리 Git에는 `HEAD`라는 특수한 포인터가 있습니다. 헤드는 지금 작업하는 로컬 브랜치를 가리킵니다.

## $ git checkout [branch-name]

branch 명령어는 브랜치를 만들 뿐 HEAD를 옮기지 않습니다. HEAD를 옮기기 위해서는 checkout 명령을 입력해야 합니다.

`$ git checkout testing`을 입력하면 HEAD는 다음과 같이 움직입니다. 

![image](https://user-images.githubusercontent.com/53790137/152642739-16a26221-c1ba-4750-82bf-44f0fd2f40a3.png)

## 참고

- [공식문서](https://git-scm.com/book/ko/v2/Git-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EB%B8%8C%EB%9E%9C%EC%B9%98%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80)
- [dogfeet](http://dogfeet.github.io/articles/2012/git-delta.html)