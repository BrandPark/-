# Class Loader

`Class Loader`는 자바 런타임 환경의 일부로써 **바이트코드로 컴파일된 클래스파일을 동적 로딩하여 JVM의 메모리에 올리는 역할**을 합니다. 

## 동적 로딩

클래스 로더는 프로그램의 모든 클래스를 한 번에 메모리에 로드하지 않고, **애플리케이션이 필요로 할 때 로드**됩니다. 

이것을 동적 로딩이라 하는데 클래스 로더는 2가지의 동적 로딩을 합니다.

#### Load-time Dynamic Loading (로드타임 동적 로딩)

클래스 로더가 클래스들의 메타 데이터를 로드할 때 우선적으로 필요한 상위 클래스의 메타 데이터가 있을 것입니다.

**클래스의 메타데이터를 로드하는 시점에 필요한 다른 클래스들의 메타데이터를 로딩**하는 것을 `로드 타임 동적로딩`이라고 합니다. 

#### Run-time Dynamic Loading (런타임 동적 로딩) 

`런타임 동적로딩`은 main 메서드가 실행된 후 **호출되는 메서드에 의해 클래스가 로드**되는 것을 말합니다. 

## Class Loader 특징

#### 1. 계층 구조

![image](https://user-images.githubusercontent.com/53790137/152683807-e6a58603-8a53-4433-b9a2-35b60ccc92db.png)

- **Bootstrap Class Loader:** JVM이 실행될 때 같이 메모리에 올라갑니다. Object 클래스를 비롯하여 Java API들을 로드합니다. 유일하게 Native code로 구현되어있습니다.
- **Platform Class Loader(Extension Class Loader):** 기본 Java API를 제외한 확장 클래스들을 로드합니다.
- **System Class Loader(Application Class Loader):** 사용자가 지정한 `$CLASS_PATH`내의 클래스들을 로드합니다.
- **User-Defined Class Loader:** 사용자가 직접 생성하는 클래스 로더입니다. 

#### 2. 위임 모델

클래스 로더는 계층 구조를 따라 클래스를 찾는 역할을 위임하는 위임 모델입니다. 

**`런타임 동적 로딩`이 일어나면 어떤 순서로 동작할까요?**

1. 이전에 로드된 클래스인지 `클래스 로더 캐시`를 확인합니다.
2. 캐시에 없다면 최상위 클래스 로더까지 올라간 후 내려오면서 찾습니다.
3. 요청을 받은 클래스로더가 마지막으로 파일 시스템에서 찾고 찾지 못한다면 `ClassNotFoundException`을 발생시킵니다. 

#### 3. Namespace

클래스 로더들은 각자 자신의 `Namespace`를 가지고 있습니다. 네임스페이스에는 로드된 클래스를 보관하는 공간으로써 클래스들을 FQCN(Fully Qualified Class Name)으로 보관합니다.

#### 4. Unload 불가

클래스 로더는 로드는 가능하지만 반대는 불가능합니다.

#### 5. 가시성 제한

클래스 로더는 자신보다 하위에 있는 네임스페이스는 볼 수 없습니다. 

## Load 과정

![image](https://user-images.githubusercontent.com/53790137/152684331-ec601edf-9bad-4918-8912-36347e224c36.png)

#### 1. Loading

클래스 로더가 클래스 파일을 읽고 바이너리 데이터를 생성한 뒤 JVM 메모리의 Method영역에 저장합니다. 로딩이 완료되면 해당 클래스 타입의 메타데이터 객체(Class<?>)를 생성해서 Heap영역에 저장합니다.

#### 2. Linking

클래스에 필요한 메모리를 할당하고 메모리에 연결 시킵니다. 작게 3단계로 나뉩니다.

- **Verifying:** 로드한 클래스 파일이 자바 언어 명세 및 JVM 명세에 명시된 대로 구성되어 있는지 검사합니다. 
- **Preparing:** 클래스 정적 변수와 같이 클래스가 필요로 하는 메모리를 할당합니다.
- **Resolving:** Symbolic memory reference를 Direct memory reference로 교체합니다.

#### 3. Initializing

클래스 변수에 초기값을 채웁니다.


