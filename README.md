# ☕ | Event Cafe Cloud 
### 📆 2022.06.24 - 2022.07.18

<div align="center">

![ec2로고](https://user-images.githubusercontent.com/93200574/179350974-ceb0af83-f6e7-4227-bf2a-e79d951539fe.png)

Event Cafe Cloud는 새로운 문화현상으로 급부상한 [이벤트카페]를 대상으로  
**카페사장님과 고객을 연결하는 <U>✨카페대여 중개플랫폼✨</U> 입니다.**
### 🔗 [프로젝트도메인](http://eventcafecloudsparta-env.eba-xh8detzp.ap-northeast-2.elasticbeanstalk.com/)

![EC2소개](https://user-images.githubusercontent.com/93200574/179353228-2d739644-d50c-4ab4-9269-13834e449e5e.jpg)
</div>

## ☕ 프로젝트 시연


## ☕ 팀원 소개
| [노태완](https://github.com/teawan-Noh)(BE)   | [강현규](https://github.com/aichyu312)(BE)  | [김예지](https://github.com/nnakki)(BE)   | [박연주](https://github.com/yeonjue-2)(BE)  |
| :-------------------------------------------- | :---------------------------------------- | :---------------------------------------------- | :------------------------------------------- |
|![노태완](https://user-images.githubusercontent.com/93200574/179357012-3c547292-b39c-48a2-9d41-1f4fd95b5873.png)|![강현규](https://user-images.githubusercontent.com/93200574/179357021-3cf4fbce-114e-4a53-8004-f803ae778362.png)|![김예지](https://user-images.githubusercontent.com/93200574/179356934-847ba189-a24f-47cd-beb0-4412a29cafcf.png)|![박연주](https://user-images.githubusercontent.com/93200574/179352676-ba9d8635-63e1-41f9-98f5-24207dbd5ed4.png)|
|    피드백을 적극적으로 주고 받는 개발자    |         함께 일하고 싶은 개발자          |   피드백을 적극적으로 주고 받는 개발자   |   함께 일하고 싶은 개발자     |
| **🔍 카페 관련 기능+배포** |**📊 게시판 관련 기능** |**👨‍👩‍👧‍👦 회원 관련 기능** |**📝 이벤트 관련 기능**|
| **메인페이지**<br />등록순 카페조회(5개) <br />카페 등록<br />s3를 연동한 파일업로드<br />카카오 주소 API 연동 <br /> **카페리스트**<br />카페 전체 리스트 조회 <br />검색기능 <br />필터 조회 기능(가격순) <br />**카페상세페이지** <br />카페 예약 내역 조회<br />달력API구현 <br />카페정보 조회 및 수정 <br />카페 리뷰 등록 및 조회,삭제 <br /> **자동 배포 구현** <br />Github Action <br />ElasticBeansTalk, RDS, ECR <br />Docker &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|  **공지 게시판** <br />접근권한설정<br />-어드민 <br />공지글 등록 및 조회  <br />공지글 수정 및 삭제 <br />**유저 게시판**<br />접근권한설정<br />-호스트,일반 <br />게시글 등록 및 조회 <br />게시글 수정 및 삭제 <br />댓글 등록 및 조회 <br />댓글 수정 및 삭제 <br />조회수 기능 구현 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | **소셜로그인**<br />JWT+SpringSecurity <br />Oauth2<br />-카카오,구글,네이버<br />회원권한설정<br />-어드민/호스트/일반 <br />**마이프로필**<br />프로필 조회 및 수정<br />예약내역 조회 및 삭제  <br />작성글 조회 및 삭제 <br />**호스트프로필**<br />카페예약내역 조회 <br />휴무일 등록 및 삭제<br />**어드민페이지**<br />회원목록<br />-조회, 권한/상태 변경 <br />호스트 신청 내역<br />-조회 및 승인<br />카페,이벤트,게시글 <br />-조회, 상태변경 | **메인페이지**<br />등록순 이벤트 조회(5개) <br /> **이벤트리스트** <br />이벤트 등록 <br />결제정보 계산 자동화<br />이벤트 전체목록 조회 <br />검색 기능 <br />필터 조회 기능<br />-카테고리별 <br />**이벤트 상세 페이지** <br />이벤트정보 조회 및 수정 <br />카페 정보 조회 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  |

## ☕ 화면정의서
![image](https://user-images.githubusercontent.com/93200574/179356485-d94978a4-7e52-41fe-9878-6f1f28a2f05e.png)
[자세히보기](https://www.figma.com/file/T4AmUoxiHMfsUwEQywFTWv/EventCloudCafe?node-id=0%3A1)

## ☕ ERD
![image](https://user-images.githubusercontent.com/93200574/179356385-c2c7af84-4232-434c-a316-d19acff65a1d.png)
[자세히보기](https://www.erdcloud.com/d/Lz8Xb2MtTkP9b3xxD)


## 🔍 [기술 특장점](https://www.notion.so/db3fac16c9d34cc4813faf4b462bca15?v=81092053b3024bc6a21eb43b6102bad0)

### [🔗]() 간편하고 안전하게 로그인하기  OAuth2.0 + JWT

### [🔗]() GitHub Actions로 자동화된 CI/CD를  적용

### [🔗]() DDD(도메인 주도 설계)를 통한 프로젝트 관리

### [🔗]() 프로젝트를 효율적으로 기획하고 관리하기


## ☕ 기술 스택
  | BE | <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> |
  | :--- | :---- |
  | FE | <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"> |
  | DB | <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/SpringDataJPA-7A1FA2?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/QueryDSL-FF4747?style=for-the-badge&logo=java&logoColor=white"> |
  | CLOUD | <img src="https://img.shields.io/badge/Amazon AWS-232F32?style=for-the-badge&logo=Amazon%20AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon%20RDS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon ECS-FF9900?style=for-the-badge&logo=Amazon%20ECS&logoColor=white"/>|
  | ETC | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"/> |




