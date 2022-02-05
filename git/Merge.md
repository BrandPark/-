# Merge

`Merge`는 **커밋들의 시퀀스를 하나로 통합시키는 작업**입니다.

쉽게 말하면 master브랜치 상태에서 `$ git merge A`를 한다면 **Master 브랜치에 A브랜치가 가진 델타들을 적용**시킵니다.

## 작동 원리

Master 브랜치에 A브랜치가 가진 델타들을 적용시킨다했습니다. 그렇다면 한가지 의문이 생깁니다.

**어디서부터 A브랜치가 가리키는 커밋까지의 델타일까?** Master와 A브랜치의 공통 조상 커밋을 알아야 델타를 적용할 수 있을 것입니다.

`Master` 브랜치 상태에서 `$ git merge iss53` 명령을 하면 다음과 같은 순서로 동작합니다.

#### 1. Master 브랜치의 커밋과 iss53 브랜치가 가리키는 커밋의 공통 조상 커밋을 찾습니다.

![image](https://user-images.githubusercontent.com/53790137/152644237-ab12d768-805b-4ba5-942d-11aca6ec6566.png)

#### 2. 공통 조상 커밋으로부터 두 커밋의 델타들을 병합하여 Merge 커밋을 만든다.

![image](https://user-images.githubusercontent.com/53790137/152644251-2999524f-e100-4db7-b01f-62ebe783c1ca.png)


위의 그림과 같이 **각 브랜치가 가리키는 커밋 두개와 공통 조상 커밋 하나를 사용하여 Merge하는 것**을 `3-way Merge`라고 합니다.

## Merge conflict

두 커밋의 델타들을 병합하는 과정에서 서로 같은 부분을 수정했을 수 있습니다. 그러면 병합 과정에서 델타가 서로 충돌하게 되는데 이것을 `Merge Conflict`라고 합니다.

충돌이 발생하면 경고 메시지와 함께 충돌이 나는 파일과 그 부분을 다음과 같이 알려 줍니다.

![image](https://user-images.githubusercontent.com/53790137/152644672-fa90df1b-da23-42cc-ae02-19ae8026eda9.png)

위의 메시지는 **document.txt파일에서 충돌이 일어나서 자동 병합이 실패했으니 충돌을 해결한 다음에 결과를 커밋하여 Merge 커밋을 직접 만들라는 것**입니다.

파일을 열어보면 다음과 같습니다.

![image](https://user-images.githubusercontent.com/53790137/152644747-a2ef233d-b914-4cfe-aadc-f8e027767fde.png)

현재 브랜치(또는 커밋)이 HEAD이고 병합하려는 브랜치가 feature입니다. **같은 파일에 같은 라인을 수정**하여 저렇게 충돌이 난 것입니다. 

아무거나 지우거나 둘 다 쓰거나 그건 병합하는 사람이 판단하여 해결한 다음에 커밋을 하면 해결할 수 있습니다.

## Fast-Forward Merge

`Fast-Forward Merge`란 **공통 조상 커밋이 두 커밋중 하나일 때** 가능한 병합 방식입니다. 

![image](https://user-images.githubusercontent.com/53790137/152644938-101b520c-4afd-44ed-9046-641527b9c69c.png)

`Main`브랜치 상태에서 `$ git merge [Some Feature 브랜치 이름]` 명령을 수행한 결과 입니다.

Main과 Some Feature의 공통 조상은 Main입니다. 즉, 두 브랜치가 선형 구조를 이루고 있습니다. 

이런 특수한 상황의 경우 `Git`은 **기본적으로 Fast-Forward Merge를 수행**하여 이름 그대로 빠르게 브랜치만 앞으로 이동시킵니다.

그래서 **병합 커밋이 만들어지지 않고 Merge를 한 티가 나지 않는 것이 특징**입니다. 


![image](https://user-images.githubusercontent.com/53790137/152645268-bbc586fb-c0bf-4dc6-9b07-b24296600076.png)

**만약 위와 같이 Fast-Forward Merge를 하지 않고 싶다면** `$ git merge --no-ff [Some Feature 브랜치 이름]`을 입력하면 됩니다.

그러면 병합 커밋이 만들어지고 Merge를 수행했다는 것을 눈으로 확인이 가능합니다.

## 참고

- [공식문서](https://git-scm.com/book/ko/v2/Git-%EB%B8%8C%EB%9E%9C%EC%B9%98-%EB%B8%8C%EB%9E%9C%EC%B9%98%EC%99%80-Merge-%EC%9D%98-%EA%B8%B0%EC%B4%88)