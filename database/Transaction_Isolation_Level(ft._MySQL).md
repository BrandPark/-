# Transaction Isolation Level

트랜잭션의 특징인 `ACID` 중 Isolation은 예외적으로 그 수준(Level)을 정할 수 있습니다.

트랜잭션의 완전한 격리(Isolation)는 멀티 쓰레드에서 데이터의 일관성을 보장하지만 Lock을 통한 성능저하가 심하기 때문이죠. 그래서 적절한 타협점을 찾아서 설정하고 인지하는 것이 중요합니다.

격리 수준에는 총 4가지가 있습니다. 격리수준이 낮은 순서대로 보겠습니다.

(이 글은 MySQL 8.0 InnoDB 기준입니다.)

#### 1. Read uncommitted

- Commit되지 않은 데이터에 다른 트랜잭션이 접근할 수 있습니다.
- Lock을 사용하지 않습니다.
- 데이터의 정합성에 문제가 많아 거의 사용하지 않습니다.

#### 2. Read committed

- Commit된 데이터에 다른 트랜잭션이 접근할 수 있습니다.
- 기본적으로 Lock을 사용하지 않고 MVCC(Multiversion Concurrency control)를 사용하여 일관된 읽기를 수행합니다.
- 한 트랜잭션에서 Read가 일어날 때마다 매번 새로운 스냅샷을 생성하고 그것을 읽습니다. 즉, 같은 읽기라도 바라보는 스냅샷이 달라 다른 결과가 나올 수 있습니다.(Non-Reatable reads)
- 읽기 잠금을 의도적으로 사용한다면 record lock만 발생하고 gap lock은 사용되지 않습니다.

#### 3. Reapeatable reads

- InnoDB의 기본 격리 수준입니다.
- 동일한 트랜잭션 내에서 일관된 읽기는 첫 번째 읽기에 의해 설정된 스냅샷을 읽습니다. 즉, 동일한 트랜잭션 내에서 Non locking select 문을 실행하는 경우 같은 결과를 보장합니다.
- 의도적으로 읽기 잠금을 사용한다면 gap lock 또는 next-key lock을 사용하여 범위를 locking합니다.

#### 4. Serializable

- 가장 단순하고 엄격한 격리수준입니다.
- 높은 데이터의 정합성을 보장하지만 그만큼 동시성 성능이 낮습니다.
- AUTOCOMMIT 옵션이 꺼져있을 경우 select 문이 ...FOR SHARE로 자동으로 변경되어 수행됩니다. 따라서 데드락이 발생하지 않는지 신중하게 계산하고 사용해야 합니다. 
- AUTOCOMMIT 옵션이 켜져있다면, Select 문은 그 자체가 트랜잭션이되어 완전히 고립됩니다. 따라서 다른 트랜잭션에 대해 차단할 필요가 없습니다.


## 참고

- [공식문서](https://dev.mysql.com/doc/refman/8.0/en/innodb-transaction-isolation-levels.html#isolevel_serializable)
- [위키피디아](https://en.wikipedia.org/wiki/Isolation_(database_systems))
- [Nesoy Blog](https://nesoy.github.io/articles/2019-05/Database-Transaction-isolation)
- [suhwan dev](https://suhwan.dev/2019/06/09/transaction-isolation-level-and-lock/)