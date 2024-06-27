스마트 와이어 VER 2.0
=====
실시간 기계 상태 알림 프로그램


프로젝트 소개
-----
- 제조 공장에 1년 간 일하면서 직접 다뤘던 기계의 문제점을 해결하고자 시작한 프로젝트 입니다.
- 모든 기계의 상태를 한 눈에 핸드폰, 컴퓨터로 확인 할 수 있습니다.
- 기계가 멈추면 핸드폰으로 알림을 보내줍니다.
- 이 때 추가로 TeamViewer, AnyDesk 등 무료 원격 제어 프로그램을 사용하여 기계를 다시 작동시킬 수도 있습니다.


### 개발 기간
- 2024.01 ~ 2024.02


### 사용 기술
| 개발     | 기술                                        |
|--------|-------------------------------------------|
| 백엔드 서버 | Java 17, Spring Boot 3, JPA |
| DB     | MySQL                        |
| 배포     | On-premise                                    |
| 웹 프론트             | -               |
| 윈도우 앱  | JavaFx(jdk 8)                             |
| 모바일 앱  | Flutter                                   |
| Push Notification | FCM |



### 구현 기능 목록

- 모바일 APP
    1. 로그인
    2. 실시간 기계 상태 확인
    3. 기계 상태 변경 시 푸시 알림
    4. 푸시 알림 설정


- 윈도우 APP (와이어 기계)
    1. 로그인
    2. 실시간 기계 상태 감지, 서버로 전송


### 모바일 앱 화면
- 모바일 앱 화면 <br>
  <img width="234" height="506" src="https://github.com/JP-company/smartwire-backend/assets/77595494/b7360340-92ee-4198-b425-971906841ab0">
  <img width="234" height="506" src="https://github.com/JP-company/smartwire-backend/assets/77595494/08466b05-ff0f-45d8-a511-6163d2799bfe">
  <br><br>

### 시연 영상

https://github.com/JP-company/smartwire/assets/77595494/ded56e60-393e-4eae-a09c-92d521559ef9

### 소개 템플릿
<img width="644" alt="smartwire-leaflet" src="https://github.com/JP-company/smartwire-backend/assets/77595494/5b28e5c5-930e-4c34-a6bd-57eaef9e6909">


### 다른 버전
1. VER 1.0, 1.1 <br>
   https://github.com/JP-company/smartwire-1.0-1.1


2. VER 1.2 <br>
   https://github.com/JP-company/smartwire-1.2
