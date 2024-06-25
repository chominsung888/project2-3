# codingStory

<details>
<summary>2차 프로젝트 기본설정</summary>


프로젝트명 : codingStory

프로그래밍 언어 : JAVA

프레임워크 : Springboot 2.7.11

라이브러리 DI : Spring WEB(MVC), Thymeleaf, Spring Data JPA, Lombok, SpringSecurity5 
               , websocket, validation, OAuth2, security  

데이터베이스 : MySql8

ORM : Spring Data JPA (JAVA(SQL))

개발툴 : IntelliJ

템플릿 엔진 : Thymeleaf (HTML + Data)

빌드 : Gradle

설정 : application.yml, application-oauth2.yml

</details>

## DEV

### MEMBER
<details>
<summary>Security</summary>


</details>

<details>
<summary>Oauth2</summary>

</details>


### attendance
<details>
<summary>근태</summary>

</details>

### approval  


![header](https://capsule-render.vercel.app/api?type=wave&color=auto&height=50px&section=header&text=개발자%20심지섭의%202차프로젝트&fontSize=50)

##목차<br>
    -[개요](#개요)<br>
    -[프로젝트 상세](#프로젝트 상세내용)<br>

## 개요
사용환경<br>

프로그램<br>
<img src="https://img.shields.io/badge/notion-white?style=flat-square&logo=notion&logoColor=gray"/>
<img src="https://img.shields.io/badge/mysql-2E64FE?style=flat-square&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/visualstudiocode-81BEF7?style=flat-square&logo=visualstudiocode&logoColor=blue"/>
<img src="https://img.shields.io/badge/intellijidea-navy?style=flat-square&logo=intellijidea&logoColor=white"/>
<img src="https://img.shields.io/badge/github-black?style=-square&logo=github&logoColor=white"/>

[//]: # (<img src="https://img.shields.io/badge/eclipseide-darkblue?style=flat-square&logo=eclipseide&logoColor=white"/>)

개발언어<br>
<img src="https://img.shields.io/badge/html5-green?style=flat-square&logo=html5&logoColor=white"/>
<img src="https://img.shields.io/badge/css3-blue?style=flat-square&logo=css3&logoColor=white"/>
<img src="https://img.shields.io/badge/auth0-ccc?style=flat-square&logo=auth0&logoColor=white"/>
<img src="https://img.shields.io/badge/chatbot-orange?style=flat-square&logo=chatbot&logoColor=white"/>
<img src="https://img.shields.io/badge/javascript-yellow?style=flat-square&logo=javascript&logoColor=white"/>
<img src="https://img.shields.io/badge/jquery-light?style=flat-square&logo=jquery&logoColor=white"/>
<img src="https://img.shields.io/badge/json-purple?style=flat-square&logo=json&logoColor=white"/>
<img src="https://img.shields.io/badge/openapiinitiative-FA5858?style=flat-square&logo=openapiinitiative&logoColor=white"/>
<img src="https://img.shields.io/badge/thymeleaf-0B610B?style=flat-square&logo=thymeleaf&logoColor=white"/>
<img src="https://img.shields.io/badge/spring-0B610B?style=flat-square&logo=spring&logoColor=white"/>
<img src="src/main/resources/static/images/readme/oAuth2.png" width="20" height="20"/> <br>
[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=Sim)](https://github.com/Sim/github-readme-stats)
[![Anurag's GitHub stats](https://github-readme-stats.vercel.app/api?username=SIM)](https://github.com/SIM/github-readme-stats)

<details>
<summary>● 프로젝트 개요</summary>
2차 프로젝트는 3차 프로젝트의 OPEN API를 연계하여 사용하기 위해 영화관으로 테마를 정했습니다.<br>
영화관에 근무하는 근무자들이 사용할 수 있는 관리자 페이지를 만들었고, 각종 기능들을 추가하였습니다.<br>
그 중 저는 근무자들이 보고서를 작성하고 결재를 받는 시스템을 만들었습니다. 

</details><br>


<details>
<summary>프로젝트 상세</summary>

# 1. 보고서 문서 작성 후 결재자 설정<br>
## ● 보고서 작성 페이지
<img src="src/main/resources/static/images/readme/img.png"/>
● ↓ 작성자는 보고서 작성시 결재자를 선택할 수 있습니다. 선택할 때 동명이인을 구분하고 가독성을 높이기 위해 부서와 직급, 이름이 모두 보이도록 코드를 작성했습니다.<br>
    결재자의 정보는 데이터베이스에 저장된 회원들의 정보를 불러와 select option 으로 불러왔습니다.   

<img src="src/main/resources/static/images/readme/img_1.png"/> <br>
  
## ● ↓보고서의 종류

   <img src="src/main/resources/static/images/readme/img_2.png"/><br>
<img src="src/main/resources/static/images/readme/img_3.png"/><br>
<img src="src/main/resources/static/images/readme/img_4.png"/><br>
<img src="src/main/resources/static/images/readme/img_5.png"/><br>
<img src="src/main/resources/static/images/readme/img_6.png"/><br>
<img src="src/main/resources/static/images/readme/img_7.png"/><br>
<img src="src/main/resources/static/images/readme/img_8.png"/><br>
<img src="src/main/resources/static/images/readme/img_9.png"/><br>
<img src="src/main/resources/static/images/readme/img_10.png"/><br>
<img src="src/main/resources/static/images/readme/img_11.png"/><br>
<img src="src/main/resources/static/images/readme/img_12.png"/><br>
<img src="src/main/resources/static/images/readme/img_13.png"/><br>
<img src="src/main/resources/static/images/readme/img_14.png"/><br>
<img src="src/main/resources/static/images/readme/img_15.png"/><br>
<img src="src/main/resources/static/images/readme/img_16.png"/><br>
<img src="src/main/resources/static/images/readme/img_17.png"/><br>
<img src="src/main/resources/static/images/readme/img_18.png"/><br>



</details>

![Footer](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=footer)
### ADMIN

<details>
<summary>admin</summary>

</details>
### FRONT

### department

<details>
<summary>부서</summary>

</details>

### pay

<details>
<summary>급여</summary>

</details>


### board

<details>
<summary>notice  공지사항</summary>
</details>

<details>
<summary>employee 우수사원</summary>
</details>

<details>
<summary>freeBoard 자유게시판</summary>
</details>

# codingstory
