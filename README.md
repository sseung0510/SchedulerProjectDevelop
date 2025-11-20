# 스케줄러 프로젝트

Spring Boot 기반의 일정 관리 서비스입니다.  
일정, 유저, 댓글 CRUD를 중심으로 회원가입/로그인(Session), 예외처리, 비밀번호 암호화, 페이징 조회까지 구현했습니다.

---

## 주요 기능

### 일정 CRUD
- 일정 생성 / 조회 / 수정 / 삭제
- 작성일·수정일은 JPA Auditing으로 자동 관리
- 유저와 연관관계(`@ManyToOne`)로 작성자를 관리

### 유저 CRUD
- 유저 생성 / 조회 / 수정 / 삭제
- 유저명, 이메일, 비밀번호, 생성/수정일 관리
- 일정은 사용자 식별자(외래키)로 유저와 연결됨

### 회원가입 & 로그인(Session)
- 이메일 + 비밀번호 기반 인증
- 로그인 시 세션 생성하여 인증 유지
- 비밀번호는 직접 구현한 `PasswordEncoder`로 Bcrypt 암호화
- 로그인 실패(이메일/비밀번호 불일치) 시 HTTP 401 반환

### 댓글 CRUD
- 일정에 댓글 생성 / 조회 / 수정 / 삭제
- 댓글은 User, Schedule 과 모두 연관관계 형성
- 작성일·수정일은 JPA Auditing 처리

### 유효성 검증 & 예외 처리
- `@Valid`, `@NotBlank`, `@Size` 등을 활용해 입력값 검증
- 공통 예외 처리 구조(`@RestControllerAdvice`)로 전체 API 응답 포맷 통일
- CustomException은 ErrorMessage 기반으로 상태코드 + 메시지 반환
- `MethodArgumentNotValidException` 발생 시 필드별 validation 메시지를 ErrorResponse로 변환

### 일정 페이징 조회
- Spring Data JPA `Pageable`, `Page` 기반 페이징 구현
- `/schedules?page=0&size=10` 형태로 요청
- 수정일 기준 내림차순 정렬
- 작성자명, 댓글 개수까지 포함된 페이지 응답 제공
- N+1 문제 해결을 위해 fetch join 및 카운트 쿼리 적용

---

## 예외 처리 구조

프로젝트 전반에 전역 예외 처리(`@RestControllerAdvice`)를 적용했습니다.

- **CustomException 처리**  
  서비스 로직에서 발생한 CustomException을 잡아서,  
  `ErrorMessage`(상태코드 + 메시지)를 기반으로 ErrorResponse를 반환합니다.

- **유효성 검증 실패 처리 (`MethodArgumentNotValidException`)**  
  `@Valid` 검증에 실패하면 해당 예외를 잡아  
  `FieldError`의 defaultMessage를 그대로 사용자에게 전달합니다.  
  공통 ErrorResponse 형식으로 timestamp, status, message 등을 내려줍니다.


<img width="1920" height="4380" alt="screencapture-notion-so-ch3-develop-2a811e412b2080aeb35fe41f78a8e111-2025-11-19-20_55_05" src="https://github.com/user-attachments/assets/59c9508a-a3e8-4aac-b365-baa265bf8151" />

<img width="426" height="199" alt="image" src="https://github.com/user-attachments/assets/ad31fc35-df00-409f-a08c-179100e70f29" />
