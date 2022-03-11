# volatile

JVM은 `코드 재정렬`과 `CPU 캐시`를 이용하여 성능을 향상시킵니다. 하지만 때때로 이러한 최적화는 예상치 못한 상황을 발생 시킵니다. 

`volatile`은 이러한 최적화에 의한 문제중 하나인 `가시성 문제`를 해결할 수 있는 키워드입니다.

## 가시성 문제

![image](https://user-images.githubusercontent.com/53790137/157777110-3868142d-a1d8-4304-8bec-65ab96aee588.png)

대부분의 최신 프로세서에서 Write 요청은 발생 후 즉시 적용되지 않고 특수 쓰기 버퍼에 대기 시킵니다. `가시성 문제`란 **하나의 스레드가 쓰기 작업을 수행했지만 다른 스레드는 그 값을 볼 수 없는 문제**를 의미합니다. 

가시성 문제의 예로 다음과 같은 시나리오를 생각해 볼 수 있습니다.

1. `스레드 A`가 공유 변수 x를 0에서 1로 수정합니다. 해당 데이터는 버퍼에 큐잉되고 Main Memory에는 아직 써지지 않았습니다.
2. `스레드 B`가 공유 변수 x를 읽습니다. Main Memory에서 x를 읽어와 캐싱합니다. 그 값은 0입니다.

1번에서 버퍼가 플러시 된 다음에 읽을 경우에도 가시성을 보장할 수 없습니다. 스레드 B가 기존에 캐싱한 데이터가 있다면 그것을 계속 사용할 수도 있기 때문입니다. 

## volatile의 가시성 문제 해결

`volatile`은 **캐싱과 버퍼를 사용하지 않고 메모리에서 직접 값을 읽고 쓰도록 하는 키워드**입니다. 하나의 스레드가 write하고 나머지 스레드는 read만 한다면 가시성을 보장합니다. 

## volatile의 Happens Before Guarantee

자바 5부터는 **volatile 변수를 write할 때 버퍼에 담겨진 데이터들을 모두 메모리에 flush하고, read할 때는 이것들을 같이 메모리에서 읽어옵니다**.

```java
// Thread A:
    sharedObject.nonVolatile = 123;
    sharedObject.counter     = sharedObject.counter + 1;

// Thread B:
    int counter     = sharedObject.counter;
    int nonVolatile = sharedObject.nonVolatile;
```

위의 코드의 시나리오는 다음과 같습니다.

1. `스레드 A`가 일반 변수인 `nonVolatile`을 write하여 버퍼에 넣고 volatile 변수인 `counter`를 write합니다. 이때 버퍼가 flush되며 counter와 함께 메모리에 쓰여집니다.
2. `스레드 B`가 volatile 변수인 `counter`를 읽습니다. 이때 함께 쓰여졌던 `nonVolatile`을 읽어와 캐싱하고 이것을 사용합니다. 

순서만 잘 지킨다면 **volatile변수가 아닌 일반 변수들의 가시성도 보장**할 수 있습니다. 

그러나 JVM은 최적화를 위해 코드의 순서를 `재정렬` 할 수 있습니다. 이 경우 의도한 대로 가시성 문제를 해결할 수 없습니다. 

이러한 상황을 피하기 위해 volatile 변수는 `happens before guarantee` 성질을 갖습니다. 이것은 **volatile 변수 이전의 변수와 이후 변수의 순서를 보장**하는 성질입니다. 

```java
    sharedObject.nonVolatile1 = 123;    // 1
    sharedObject.nonVolatile2 = 456;    // 2
    sharedObject.nonVolatile3 = 789;    // 3

    sharedObject.volatile     = true; //a volatile variable

    int someValue1 = sharedObject.nonVolatile4; // 4
    int someValue2 = sharedObject.nonVolatile5; // 5
    int someValue3 = sharedObject.nonVolatile6; // 6
```

위의 코드에서 volatile변수 이전에 쓰인 `1~3번`은 그 안에서 순서가 재정렬 될 수 있지만 volatile 변수 이전에 존재하는 것을 보장합니다. 

같은 맥락으로 `4~6번`도 그 안에서 순서가 재정렬 될 수 있지만 volatile 변수 이후에 존재하는 것을 보장합니다. 

## volatile의 한계

`volatile`은 다음과 같은 한계가 있습니다.

- **쓰기 작업이 하나의 스레드에서만 이루어져야 가시성이 보장**됩니다. 쓰기 작업이 여러 스레드에 의해 이루어진다면 `race condition` 문제가 발생할 수 있습니다. 
- volatile은 JVM의 성능 향상을 위한 코드 재정렬과 캐싱, 버퍼를 그대로 사용하지 못하기 때문에 성능적인 손해를 볼 수 있습니다. 

## volatile vs synchronized

`synchronized`는 상호 배제를 통해 가시성과 동시성 문제를 해결하는 키워드입니다. 하지만 하나의 스레드만 코드의 실행을 허용하기 때문에 성능이 떨어집니다. 

`volatile`은 쓰기 작업을 하나의 스레드에서만 처리된다면 나머지 스레드가 동시에 읽어도 가시성을 보장하기 때문에 좋은 성능을 보일 수 있습니다. 

## 참고
- https://www.baeldung.com/java-volatile
- https://parkcheolu.tistory.com/16