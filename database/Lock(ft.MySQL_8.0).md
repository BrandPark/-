# Lock

이번 포스팅에서는 `MySQL InnoDB`의 Lock에 대해서 알아보겠습니다.

`InnoDB`는 **Lock을 기반으로 ACID와 동시성을 보장**합니다. 보통 Isolation level에 따라 자동으로 수행되며 명시적으로 걸수도 있습니다.

InnoDB의 Lock은 여러 유형이 있습니다. 차례대로 알아보겠습니다.

(`MySQL 8.0`의 공식문서를 보고하여 작성하였으며, 인용문또한 공식문서에서 스크랩한 것입니다.)

#### Shared & Exclusive Locks

> InnoDB implements standard row-level locking where there are two types of locks, shared (S) locks and exclusive (X) locks.

`InnoDB`는 두 가지 유형의 row-level(행 수준) 잠금을 구현합니다. 

- **Shared lock(이하 S-Lock)**: lock을 가지고 있는 트랜잭션이 행에 대한 Read 권한을 가집니다. 즉, 읽을 때 거는 Lock입니다.
- **Exclusive lock(이하 X-Lock)**: lock을 가지고 있는 트랜잭션이 행에 대한 Update, Delete 권한을 가집니다. 즉, 쓸 때 거는 Lock입니다.

**만약 트랜잭션 T1이 행 r에 s-lock을 걸었을 때, 같은 행에 대한 다른 트랜잭션 T2의 lock 요청은 어떻게 될까요?**
- T2가 `S-Lock`을 시도한다면 즉시 수용됩니다.
- T2가 `X-Lock`을 시도한다면 즉시 수용되지 않고 T1의 Lock이 해제될 때까지 기다립니다.

**이번엔 트랜잭션 T1이 행 r에 대해 x-lock을 걸었을 때, T2의 lock 요청은 어떻게 될까요?**
- T2는 r에 대해 x, s 잠금 모두 불가능합니다. T2는 T1이 잠금을 해제할 때까지 기다립니다.


정리하면 이름에서 알 수 있듯이 `Shared Lock`은 **공유 잠금**으로써 같은 행에 대한 S-Lock 요청은 허용하여 Lock을 공유할 수 있습니다.
`Exclusive Lock`은 **독점 잠금**으로써 같은 행에 대한 모든 Lock 요청을 허용하지 않습니다. 


#### Intention Locks

`Intention Locks`은 **누군가 테이블의 행을 잠글 예정임을 명시적으로 표시하는 table-level Lock**입니다.

- **Intention shared lock(IS)**: 트랜잭션이 테이블의 행에 s-lock을 걸려고 한다는 것을 암시합니다. `SELECT ... FOR SHARE`와 같이 사용합니다.
- **Intention exclusive lock(IX)**: 트랜잭션이 테이블의 행에 x-lock을 걸려고 한다는 것을 암시합니다. `SLECT ... FOR UPDATE`와 같이 사용합니다.

트랜잭션이 테이블의 행에 S-Lock이나 X-Lock을 걸려면 먼저 IS나 IX를 걸어야 합니다. 

|   |X |IX |S  |IS |
|---|---|---|---|---|
|X  |충돌|충돌|충돌|충돌|
|IX |충돌|호환|충돌|호환|
|---|---|---|---|---|
|S  |충돌|충돌|호환|호환|
|IS |충돌|호환|호한|호환|

각 잠금이 공존 가능한지 나타낸 표입니다. 충돌인 경우에는 기존 잠금이 해제될 때까지 기다립니다. 

표를 보다시피 Intention Lock 끼리는 서로 차단하지 않습니다. 주요 목적이 락이 걸릴 예정임을 보여주는 것에 있기 때문입니다. 

#### Record Locks

> A record lock is a lock on an index record. 

`Record Locks`은 인덱스 레코드에 대한 잠금입니다. 인덱스가 설정되어 있지 않더라도 InnoDB는 Hidden cludstered Index를 생성하고 레코드 잠금에 사용합니다. 

```SQL
	 SELECT c1 FROM t WHERE c1 = 10 FOR UPDATE	
	 -- 다른 트랜잭션이 t.c1 값이 10인 레코드에 대해 삽입, 업데이트, 삭제하는 것을 차단합니다.
```

#### Gap Locks

> A gap lock is a lock on a gap between index records, or a lock on the gap before the first or after the last index record.
> ...
> Gap locks are part of the tradeoff between performance and concurrency, and are used in some transaction isolation levels and not others.
> Gap locking is not needed for statements that lock rows using a unique index to search for a unique row. (This does not include the case that the search condition includes only some columns of a multiple-column unique index; in that case, gap locking does occur.)

`Gap Locks`는 레코드 사이(gap)를 잠금으로써 **gap에 새로운 레코드가 삽입되는 것을 방지**합니다. (범위 잠금이라고도 부릅니다.)

Transaction Isolation level 중 몇개의 레벨에서 자동으로 사용됩니다.

unique index를 조건으로 유일한 행을 검색할 때는 Gap Lock이 설정되지 않습니다. (다중 컬럼 인덱스의 경우 인덱스의 모든 컬럼이 조건으로 사용되어야 Gap Lock이 설정 될 수 있습니다.)

```SQL
	SELECT * FROM t WHERE c1 BETWEEN 10 AND 13 FOR UPDATE
	-- 아래의 Gap에 Lock을 걸어 Gap에 새로운 행이 삽입되는 것을 방지합니다.
	-- c1 < 10
	-- 10 < c1 < 11 && 11 < c1 < 12 && 12 < c1 < 13
	-- c1 > 13
```

트랜잭션 격리 수준을 `Read Committed`인 경우 **Gap 잠금이 비활성화** 됩니다. 이 경우 검색 및 인덱스 스캔에 대해 간격 잠금이 비활성화 되고, 외래 키 제약 조건 검사 및 중복 키 검사에만 사용됩니다.

Gap Lock의 목적은 Gap에 새로운 행이 삽입되는 것을 방지하는 것으로써 `FOR Update`와 `FOR Share`는 모두 같은 기능으로 수행되며 공존이 가능합니다.

#### Next-Key Locks

> A next-key lock is a combination of a record lock on the index record and a gap lock on the gap before the index record.

`Next-Key Locks`는 **Gap Locks 와 Record Locks의 혼합**입니다. 

```SQL
	SELECT * FROM t WHERE c1 BETWEEN 10 AND 13 FOR UPDATE
	-- 아래와 같이 Lock이 설정됩니다. 
	-- c1 = 10, 11, 12, 13	[Record x-lock]
	-- c1 < 10	[Gap lock]
	-- 10 < c1 < 11 && 11 < c1 < 12 && 12 < c1 < 13	[Gap lock]
	-- c1 > 13	[Gap lock]
```

InnoDB의 기본 Isolation level인 `REPEATABLE READ`에서 검색 및 인덱스 스캔에 `Next-key lock`을 사용하여 `Phantom read`를 방지합니다.


## 참고

- [공식문서](https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html)
- [Taes0k DevLog](https://taes-k.github.io/2020/05/17/mysql-transaction-lock/)