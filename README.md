# Mission
likelion backend mission


## 프로젝트
일반 사용자는 중고거래를, 사업자는 인터넷 쇼핑몰을 운영할 수 있게 해주는 쇼핑몰 사이트



## 실행 및 테스트


```sh
git clone https://github.com/PJScript/Mission2_ParkJunSu.git
```
클론 후 파일을 실행 해주세요

아래 파일을 다운 받고 postman 에서 테스트 해주세요 <br />
[misson.postman_collection.json](https://github.com/PJScript/Mission2_ParkJunSu/files/14490775/misson.postman_collection.json)

## 기능 구현 내용
#### 중고거래 ( 진행중 )
- 로그인
- 회원가입
- 유저정보 조회
- 유저정보 수정
- 유저 중복체크
- 물품등록
- 물품조회
- 물품수정
- 물품삭제
#### 스토어 ( 미완료 )
#### 추가 과제 ( 미완료 )






### 세부내용
- 로그인시 JWT 발급 
- 회원가입시 비밀번호 암호화 
- 유저정보 조회시 본인 데이터만 조회할 수 있게 권한 체크
- 유저정보 수정시 본인 데이터만 조회할 수 있게 권한 체크
- 유저정보 중복체크
- 물품 등록시 활성유저만 가능하도록 권한 체크
- 물품 조회시 활성유저만 가능 하도록 권한 체크
- 물품 수정시 본인의 게시글인지 JWT와 비교하여 권한 체크
- 물품 삭제시 본인의 게시글인지 JWT와 비교하여 권한체크
- DB를 활용한 RBAC ( Role Based Access Control ) 적용 해보았음
https://github.com/PJScript/Mission2_ParkJunSu/blob/45698d0c4aeab3667f23e348415cae19b6ed5a21/src/main/java/com/example/storeweb/common/security/DynamicUrlFilter.java#L1-L101
- Activity 테이블에서 엔드포인트별 접근 가능한 권한을 추가하거나 수정할 수 있음
https://github.com/PJScript/Mission2_ParkJunSu/blob/45698d0c4aeab3667f23e348415cae19b6ed5a21/src/main/java/com/example/storeweb/domain/auth/entity/ActivityEntity.java#L11-L36
- Role 테이블에서 권한을 추가할 수 있음
- Status 테이블에서 서비스내의 모든 상태 값을 정의할 수 있음 ( 판매중, 판매완료, 사업자 전환 요청중 등 )
- 공통 에러 및 응답을 정의할 수 있음
https://github.com/PJScript/Mission2_ParkJunSu/blob/45698d0c4aeab3667f23e348415cae19b6ed5a21/src/main/java/com/example/storeweb/common/GlobalSystemStatus.java#L1-L31
- 공통 에러를 핸들링 할 수 있음
https://github.com/PJScript/Mission2_ParkJunSu/blob/45698d0c4aeab3667f23e348415cae19b6ed5a21/src/main/java/com/example/storeweb/common/security/ExceptionHandlerFilter.java#L1-L49





## Commit
feat : 새로운 기능에 대한 커밋 <br />
fix : 버그 수정에 대한 커밋  <br />
build : 빌드 관련 파일 수정에 대한 커밋  <br />
chore : 기타 여러가지 작업  <br />
ci : CI관련 설정 수정에 대한 커밋  <br />
docs : 문서 수정에 대한 커밋  <br />
style : 코드 스타일 혹은 포맷 등에 관한 커밋  <br />
refactor :  코드 리팩토링에 대한 커밋  <br />
test : 테스트 코드 수정에 대한 커밋  <br />
