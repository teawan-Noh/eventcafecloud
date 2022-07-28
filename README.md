# ☕ | Event Cafe Cloud 
### 📆 2022.06.24 - 2022.07.28

<div align="center">

![ec2로고](https://user-images.githubusercontent.com/93200574/179350974-ceb0af83-f6e7-4227-bf2a-e79d951539fe.png)

Event Cafe Cloud는 새로운 문화현상으로 급부상한 [이벤트카페]를 대상으로  
**카페사장님과 고객을 연결하는 <U>✨카페대여 중개플랫폼✨</U> 입니다.**
### 🔗 [www.eventcafecloud.com](http://www.eventcafecloud.com/)

![EC2소개](https://user-images.githubusercontent.com/93200574/179353228-2d739644-d50c-4ab4-9269-13834e449e5e.jpg)
</div>

## ☕ 프로젝트 시연


## ☕ 팀원 소개
| [노태완](https://github.com/teawan-Noh)(BE)   | [강현규](https://github.com/aichyu312)(BE)  | [김예지](https://github.com/nnakki)(BE)   | [박연주](https://github.com/yeonjue-2)(BE)  |
| :-------------------------------------------- | :---------------------------------------- | :---------------------------------------------- | :------------------------------------------- |
|![노태완](https://user-images.githubusercontent.com/101540771/181504776-1f44798c-f240-48d2-b050-ce9f8abd66e4.png)|![강현규](https://user-images.githubusercontent.com/93200574/179357021-3cf4fbce-114e-4a53-8004-f803ae778362.png)|![김예지](https://user-images.githubusercontent.com/93200574/179356934-847ba189-a24f-47cd-beb0-4412a29cafcf.png)|![박연주](https://user-images.githubusercontent.com/93200574/179393128-68fd44d6-99b6-466b-9e87-6f0db0961ffa.png)|
|    📢 소통을 중요시하는, 끊임없이 성장하는 개발자입니다    |        📢 "왜?"라고 물어보는, 에러 찾는 걸 좋아하는 개발자입니다    |   📢 노력의 결과를 믿는, 지속적인 성장을 추구하는 개발자입니다.   |   📢 커뮤니케이션이 원활한 협업하고 싶은 개발자입니다     |
| **🔍 카페 도메인+배포** |**📊 게시판 도메인** |**👨‍👩‍👧‍👦 회원 도메인** |**📝 이벤트 도메인**|
| **메인페이지**<br />등록순 카페조회(5개) <br />카페 등록<br />s3를 연동한 파일업로드<br />카카오 주소,지도 API 연동 <br /> **카페리스트**<br />카페 전체 리스트 조회 <br />검색기능 <br />필터 조회 기능(가격순) <br />**카페상세페이지** <br />카페 예약 내역 조회<br />-달력API구현 <br />카페정보 조회 및 수정 <br />카페 리뷰 등록 및 조회,삭제 <br /> **자동 배포 구현** <br />Github Action <br />ElasticBeansTalk, RDS, ECR(Docker) <br />Route53 <br />북마크 기능 구현 <br /> swagger(api 문서 자동화) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|  **공지 게시판** <br />접근권한설정<br />-어드민 <br />공지글 등록 및 조회  <br />공지글 수정 및 삭제 <br />**유저 게시판**<br />접근권한설정<br />-호스트,일반 <br />게시글 등록 및 조회 <br />게시글 수정 및 삭제 <br />댓글 등록 및 조회 <br />댓글 수정 및 삭제 <br />조회수 기능 구현 <br />XSS 보안강화 <br /> 부하테스트 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; | **소셜로그인**<br />JWT+SpringSecurity<br />-회원권한설정<br />--어드민/호스트/일반  <br />Oauth2<br />-카카오,구글,네이버<br />**마이프로필**<br />프로필 조회 및 수정<br /> - 닉네임 유효성 검사<br />예약내역 조회 및 삭제  <br />작성글 조회 및 삭제 <br /> 내 북마크(카페, 이벤트)<br />**호스트프로필**<br />내 카페 관리<br />-카페예약내역 조회 <br />-휴무일 등록 및 삭제<br />**어드민페이지**<br />회원목록<br />-조회, 권한/상태 변경 <br />호스트 신청 내역<br />-조회 및 승인<br />카페,이벤트,게시글 <br />-조회, 상태변경 <br />-필터, 페이징 전체 적용 <br />메일링 서비스 | **메인페이지**<br />등록순 이벤트 조회(5개) <br /> **이벤트리스트** <br />이벤트 등록 <br /> 접근권한설정<br />결제정보 계산 자동화<br />이벤트 전체목록 조회 <br />검색 기능 <br />필터 조회 기능<br />-카테고리별 <br />**이벤트 상세 페이지** <br />이벤트정보 조회 및 수정 <br />이벤트 정보 삭제 <br /> 댓글 등록 <br /> 댓글 조회 및 삭제 <br /> 페이징 <br />카페 정보 조회 <br /> 북마크 기능 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  |

## ☕ 화면정의서
![image](https://user-images.githubusercontent.com/93200574/179791273-31f7e899-a321-4905-8307-b9c856a699a9.png)
[자세히보기](https://www.figma.com/file/T4AmUoxiHMfsUwEQywFTWv/EventCloudCafe?node-id=0%3A1)

## ☕ ERD
![image](https://user-images.githubusercontent.com/101540771/181507544-f1b7b3da-571f-4d77-83f5-226248c9f08c.jpg)

[자세히보기](https://www.erdcloud.com/d/Lz8Xb2MtTkP9b3xxD)

## ☕ 화면 구현 
| **📋 메인 페이지** | **📋 로그인 페이지** |
|----------|-----------|
|![메인페이지](https://user-images.githubusercontent.com/101540771/181512329-3a2b70c9-d40a-462f-8777-aa8b27b30c92.gif)|![로그인페이지](https://user-images.githubusercontent.com/101540771/181514074-2ee0ca3a-3e37-45ee-9ccf-826a9f3172a1.gif)|

| **📋 프로필 페이지** | **📋 카페 페이지** |
|----------|-----------|
|![프로필페이지](https://user-images.githubusercontent.com/101540771/181514180-0ddaac1f-3cb2-4894-87e4-95e6b9233f6d.gif)|![카페페이지](https://user-images.githubusercontent.com/101540771/181514369-254337b8-24ca-4433-baeb-e304e3cadf05.gif)|

| **📋 어드민 페이지** | **📋 이벤트 페이지** |
|----------|-----------|
|![어드민](https://user-images.githubusercontent.com/101540771/181514270-8c67163f-5f9d-4d80-9e6a-98b8a96bdb8e.gif)|![이벤트](https://user-images.githubusercontent.com/101540771/181514441-43e20c35-e1fe-46b0-a5bb-a9dcf573b35f.gif)|


| **📋 게시판 페이지** | **📋 에러 페이지** |
|----------|-----------|
|![게시판페이지](https://user-images.githubusercontent.com/101540771/181516164-706bcecf-bff6-419a-9f55-012565ed0ada.gif)|![에러페이지](https://user-images.githubusercontent.com/101540771/181516268-784e1231-3dc4-4391-a6ec-4652dcfc7129.gif)|

## 🔍 [기술 특장점](https://iridescent-alder-11d.notion.site/EC2-a5b2cf235a774823aba067a534f1826e)

### [🔗](https://iridescent-alder-11d.notion.site/SpringSecurity-JWT-OAuth2-0-cbd503c8a6a54cf6beefe788909bd9ed) SpringSecurity와 JWT를 사용한 OAuth2.0 소셜로그인

### [🔗](https://iridescent-alder-11d.notion.site/AWS-Github-Action-Docker-CI-C-3b5ac35ec4444b1e900525c1c4936463) [AWS, Github Action] Docker를 이용한 CI/CD

### [🔗](https://iridescent-alder-11d.notion.site/XSS-cross-site-scripting-dbd15c7ebb884f22ad5deb61fc843b2d) XSS(cross-site scripting) 공격 대비 보안 강화

### [🔗](https://iridescent-alder-11d.notion.site/35e60926d5f6480496e66ef9456f9cef) 프로젝트를 효율적으로 기획하고 관리하기

### [🔗](https://iridescent-alder-11d.notion.site/JMeter-222eee8dcf2e49afbb3f2c5ed0abe671) JMeter 부하 테스트를 이용한 서버 최적화



## ☕ 기술 스택
  | BE | <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> |
  | :--- | :---- |
  | FE | <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"> <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"> |
  | DB | <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/SpringDataJPA-7A1FA2?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/QueryDSL-FF4747?style=for-the-badge&logo=java&logoColor=white"> |
  | CLOUD | <img src="https://img.shields.io/badge/Amazon AWS-232F32?style=for-the-badge&logo=Amazon%20AWS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon%20RDS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon ECS-FF9900?style=for-the-badge&logo=Amazon%20ECS&logoColor=white"/> <img src="https://img.shields.io/badge/Route53-90E59A?style=for-the-badge&logo=Route53&logoColor=white"/> |
  | ETC | <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white"/> |





