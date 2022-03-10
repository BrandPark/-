# Delete vs Truncate vs Drop

테이블 데이터를 삭제할 때 사용되는 `Delete`, `Truncate`, `Drop`의 차이점에 대해 공부하였습니다.

# Delete

- DML 명령입니다.
- 삭제하기 전에 레코드들을 스캔하는 작업을 거칩니다.
- 레코드가 삭제되도 저장공간을 반납하지 않습니다.
- 인덱스가 삭제되지 않습니다. 
- Commit 이전에 Rollback이 가능합니다.

# Truncate

- DDL 명령입니다.
- 스캔작업 없이 모든 레코드를 삭제합니다.
- 저장공간을 반환합니다.
- 인덱스가 삭제됩니다. 
- Rollback이 불가능합니다.

# Drop

- DDL 명령입니다.
- 테이블을 삭제합니다.
- 테이블의 저장공간을 모두 반환합니다.
- Rollback이 불가능합니다.

# 모든 레코드 삭제하기(Delete vs Truncate)

모든 레코드를 삭제하는 방법은 `Delete`와 `Truncate`가 있습니다. 다음과 같은 차이점이 있습니다.

## 속도

`Delete`는 Where절을 통해 조건을 검사하기 때문에 기본적으로 하나의 행씩 검사하여 지웁니다.

`Truncate`는 무조건 모든 레코드를 삭제하기 때문에 `Delete`보다 빠릅니다. 

## 저장공간 반환

`Delete`는 저장공간을 유지한 상태로 레코드를 삭제합니다.

`Truncate`는 저장공간을 모두 반환하고 심지어 인덱스까지 삭제하여 처음 테이블을 생성했을 때의 모습으로 돌려놓습니다.

# 참고
- https://developer-c.tistory.com/54
- https://afteracademy.com/blog/what-is-the-difference-between-truncate-delete-and-drop-statements#:~:text=Unlike%20TRUNCATE%20which%20only%20deletes,the%20table%20from%20the%20database.
- https://www.geeksforgeeks.org/difference-between-delete-drop-and-truncate/

