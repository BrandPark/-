# Transaction Isolation Level

트랜잭션의 특징인 `ACID` 중 Isolation은 예외적으로 그 수준(Level)을 정할 수 있습니다.

트랜잭션의 완전한 격리(Isolation)는 멀티 쓰레드에서 데이터의 일관성을 보장하지만 Lock을 통한 성능저하가 심하기 때문이죠. 그래서 적절한 타협점을 찾아서 설정하고 인지하는 것이 중요합니다.

격리 수준에는 총 4가지가 있습니다. 격리수준이 낮은 순서대로 보겠습니다.

#### 1. Read uncommitted

**커밋되지 않은 트랜잭션의 변경내용을 읽을 수 있는 수준**입니다. 

Isolation이 없다고 봐도 무방하고 데이터의 **정합성에 문제**가 많기 때문에 잘 사용되지 않습니다.

#### 2. Read committed

**데이터를 읽는 순간 커밋된 데이터임을 보장하는 격리 수준**입니다. 쉽게 말해 커밋된 데이터만 읽을 수 있습니다. 

`Read uncommitted`의 문제점중 하나인 `Dirty Read`를 막습니다. 

하지만 읽는 순간에만 보장하기 때문에 **`Non-repeatable reads`문제**가 있습니다. 

`Non-repeatable read`는 **한 트랜잭션에서 반복해서 같은 레코드를 읽었을때 같음을 보장하지 않는 문제**를 의미합니다.

#### 3. Reapeatable reads

**Reapeatable reads를 보장하는 격리수준**입니다. 

읽을 때 트랜잭션 시작 순간부터의 Undo log에 있는 데이터를 읽기 때문에 다른 트랜잭션에 의해 수정된 데이터를 읽는 것을 막습니다.

하지만 처음에는 안보였던 레코드가 중간에 다른 트랜잭션에 의해 Insert되어 갑자기 나타나는 **`Phantom read`가 발생**할 수 있습니다.

#### 4. Serializable

**가장 단순하고 엄격한 격리수준**입니다. 완전한 트랜잭션의 격리를 통한 데이터의 정합성을 보장하지만 **동시성 성능이 가장 낮습니다.** 


## 참고

- [위키피디아](https://en.wikipedia.org/wiki/Isolation_(database_systems))
- [Nesoy Blog](https://nesoy.github.io/articles/2019-05/Database-Transaction-isolation)