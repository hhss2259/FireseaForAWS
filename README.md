# 프로젝트 포트폴리오

작성자 : 최홍석(백엔드 개발자)

수정일 : 2023년 7월 25일

**프로젝트 상세히 보기**

https://velog.io/@hhss2259/series/Firesea-Project

**GitHub 주소**

https://github.com/hhss2259/FireseaForAWS



💡 **“개발자들끼리 소통 가능한 간단한 커뮤니티 개발”**
학습한 기술들을 종합적으로 사용하여 웹 프로젝트 **제작부터 배포까지 모든 과정**을 학습하는 것을 목표로 합니다.



## 💫 프로젝트 정보

---

        유형       개인 프로젝트

        기간       2023년 4월 ~ 2023년 5월

        인원       총 2명 (프론트엔드 개발자 1명, 백엔드 개발자 1명)

    간단 요약      학습한 지식들을 종합적으로 사용하여 백엔드 서버 구축 및 AWS를 통한 배포

    사용 기술      JPA, Querydsl, SpringBoot, MariaDB, Docker, AWS, Nginx, React, Rest API


**구체적 내용  **
최대한 실무와 비슷한 형태의 웹 사이트를 개발하자는 목표로 프론트엔드 개발자를 희망하는 친구 1명과 개인 프로젝트를 진행하였습니다.  프로젝트 주제는 **‘개발자들끼리 소통 가능한 간단한 커뮤니티’** 이었으며 웹 서버 구축부터 배포까지 전반적인 과정을 학습하는 것이 목표였습니다. 해당 과정은 다음과 같습니다

1. JPA, Querydsl, SpringBoot를 사용하여 게시판 기능을 구현한 백엔드 서버를 구축하였으며, AWS를 통한 scale-out 을 고려하여 JWT를 사용한 로그인 방식을 구현
2. AWS의 EC2 인스턴스 1개와 RDS 인스턴스 1개(MariaDB)를 사용하여 가장 기본적인 형태의 운영 환경을 구축
3. Docker를 사용하여 프론트엔드 개발자가 구현한 React 프로젝트와 직접 구현한 Spring 프로젝트, 리버스 프록시 역할을 담당하는 Nginx 프로젝트를 통합하여 EC2 인스턴스에 배포하였습니다.

위와 같은 과정을 거치며 통상적인 웹 어플리케이션의 흐름과 구조에 대해서 이해할 수 있었습니다. 
또한 React 와 같은 클라이언트 사이드 렌더링 방식을 사용하는 프로젝트와 협력 방안에 대해서 고민해볼 수 있었으며 이를 통해 효율적인 API 개발에 대해 생각해보게 된 계기가 되었습니다.
<br/>

## 🥇 **프로젝트의 주요 목표**

---

![](https://velog.velcdn.com/images/hhss2259/post/25f2f700-2207-4e3d-be9b-86e1e169b582/image.png)

1. 이제까지 각 부분 별로 학습한 기술 스택들을 **종합적으로 사용**하여 백엔드 서버 구축
2. 백엔드 서버 **구현부터 배포까지 전반적인 웹 개발의 과정** 학습
3. 개발자들끼리 정보를 공유할 수 있는 **간단한 커뮤니티** 개발
<br/>

## 🧑‍사용 기술 및 **구성원**

---
### 사용 기술

![](https://velog.velcdn.com/images/hhss2259/post/2a8bd3b4-b5ea-49c3-ae83-0116a7f0330c/image.png)

### **프론트엔드 개발자 1명**

- 프론트엔드 개발자를 희망하는 팀원이 담당
- 사용 기술
    - html, css3, javascript
    - React

### **백엔드 개발자 1명 - 최홍석**

- 스프링부트를 이용한 백엔드 서버 구현
- 완성된 웹 어플리케이션을 통합하여 AWS에 배포
- backend
    - Java `11`
    - SpringBoot `2.7.9`
    - MariaDB
    - JPA  `2.7.8`
    - Querydsl `5.0.0`
- deploy
    - AWS
    - Docker
    - Nginx

## 🔎**세부 구성도**

---

![](https://velog.velcdn.com/images/hhss2259/post/4bb5a2db-b1b6-45f7-87d4-8d2a30001005/image.png)


### 1. Nginx 컨테이너 (리버스 프록시 역할 / 80번 포트)

- 사용자가 접근 시 해당 요청을 최우선적으로 받고, 요청 url에 따라 Web Server(React 서버)와 WAS(SpringBoot 서버) 둘 중 하나로 라우팅을 해주는 **리버스 프록시 역할**을 맡는다
- **‘/api’ 로 시작하는 url**은 WAS로 라우팅된다
- **그 외의 url**은 Web Server로 전송된다.

### 2. Nginx + React 컨테이너 (Web Server 역할 / 3000번 포트)

- 리버스 프록시 역할을 하는 Nginx 컨테이너와 다른 Nginx 컨테이너다.
- React 프로젝트를 빌드 후 산출된 결과물을 Nginx 안에 옮겨 담는다. 이후 Nginx가 정적 파일을 제공한다

### 3. SpringBoot 컨테이너 (WAS 역할 / 8080번 포트)

- WAS 서버로 비지니스 로직을 수행하고 DB에 접근해서 데이터를 CRUD 할 수 있다.

## ⚙️**AWS 아키텍처**

---

![](https://velog.velcdn.com/images/hhss2259/post/23e3216a-8818-4b93-8f3f-e409a9d6e0df/image.png)


가장 기초적인 형태로 EC2 인스턴스 1개(서버 역할)와 RDS 인스턴스 1개(데이터베이스 역할)를 사용

## ☄️디렉터리 구조

---

![](https://velog.velcdn.com/images/hhss2259/post/1283bf2b-e3ac-4af9-a7ca-1e957da14fdc/image.png)


1. **backend 폴더** 
    - SpringBoot로 만든 WAS
2. **db 폴더** 
    - 로컬 환경에서 개발 시 DB 환경을 구성을 위해 사용한 DB 컨테이너(Docker).
    - 실제 배포 환경에서는 AWS의 RDS 인스턴스를 사용하기 때문에 사용하지 않는다
3. **frontend 폴더**
    - React 프로젝트
    - 실제 배포 시 Nginx를 Web Server로 사용하도록 Dockerfile 구성
4. **nginx 폴더**
    - 배포 시 WAS와 Web Server를 라우팅해주는 리버스 프록시 컨테이너
    - 향후 로드밸런스로도 사용할 수 있으며 https 기능을 제공할 수도 있기에 유용하다(미개발)
5. **compose-dev.yml**
    - 개발 환경에서 사용한 docker compose 파일
6. **compose-dev-aws.yml**
    - AWS의 EC2 인스턴스 환경에서 사용한 docker compose 파일
<br/>

## 👨‍🔧구현 내용

---
### 기능적 관점

1. **게시글 CRUD 기능 + 검색 기능 + 페이징 기능**
    - 프로그래밍에서 가장 기본적으로 가장 기본적으로 요구되는 기능
    - **JPA**와 **Querydsl**을 통해서 더 효율적으로 DB에 접근하는 방법들을 탐구
    
2. **로그인 기능**
    - **JWT 사용**
        - AWS에서 **scale-out 시 고려해볼 수 있는 방법
        - 스프링의 인터셉터를 사용해 로그인이 필요한 접근 시 인터셉터가 JWT 토큰을 검증
        - 보안 기능 향상을 위해 **Access token과 Refresh token **두 가지 토큰을 사용했다.
    - **이메일 인증**
        - 모든 개인 정보를 직접 입력 받는 것은 토이 프로젝트에서 보안 문제로 인해 제한이 크다고 판단 ⇒ 이메일 인증만을 요구하는 로그인 방식을 선택
        - **구글의 메일 서비스**를 통해 실제 이메일 인증이 가능하도록 구현했다.
    - **비밀번호** **암호화를 위해 SpringSecurity를 부분적으로만 적용**
        - 비밀번호를 DB에 암호화하여 저장하기 위해서 SpringSecurity의 BycryptEncoder 사용
    - **실패 사례 : SNS 로그인**
        - 로컬 환경에서는 구현했으나 실제 배포 환경에서는 사용하지 못했다.
    
3. **마이 페이지 기능**
    - 닉네임 변경
    - 작성한 글의 갯수 및 목록을 출력할 수 있는 기능
    
4. **게시판 좋아요 싫어요 기능**
    - 게시글에 추천 혹은 비추천을 선택할 수 있다.
    - **클라이언트의 ip**를 기준으로 게시글 당 1일 1회만 사용 가능하도록 기능을 제한
    
5. **게시판 댓글 CRUD 기능**
    - 게시글에 댓글을 작성하고 수정, 삭제할 수 있는 기본적인 기능
    - Querydsl을 통해 테이블 조인 쿼리 작성

### [배포]

1. **domain**
    - ‘내도메인.com’이라는 무료 도메인 사이트를 통해 무료 도메인 사용

1. **AWS**
    - VPC , 서브넷, 라우팅 테이블 등 기본적인 네트워크 환경 구성
    - EC2 인스턴스와 RDS 인스턴스를 사용해서 가장 기본적인 어플리케이션 구성 사용

1. **Docker**
    
    두 가지 방법을 통해 AWS EC2 인스턴스에 서버를 배포
    
    - 방법 1 -  Docker-Compose + GitHub 사용하**기**
    - 방법 2 - DokerHub 사용하기

1. **NGINX**
    - 실제 배포 환경에서는 Nginx가 Web Server로 동작, 정적 파일을 제공하는 역할
    - Web Server(정적 파일을 제공, React서버)와 WAS(동적 데이터를 제공, Spring 서버)를 라우팅하는 **리버스 프록시 객체로 사용**
<br/>

## 🔦 **간단한 UI 모음**

---
1. **게시글 페이징**
![](https://velog.velcdn.com/images/hhss2259/post/c7a7235f-3c71-4d40-b5b2-3e9441735844/image.png)


---

2. **게시글 검색하기**
![](https://velog.velcdn.com/images/hhss2259/post/812ad512-9c40-4975-804d-b9b43790624c/image.png)


---

3. **댓글 및 좋아요 기능**
![](https://velog.velcdn.com/images/hhss2259/post/073dd72f-0ce1-41f1-8cc7-a60f9c542c23/image.png)


---

4. **마이 페이지 기능(닉네임 변경 및 내가 작성한 글 목록)**
![](https://velog.velcdn.com/images/hhss2259/post/73a0736d-c9fe-44a4-a3b9-d92451ba62c3/image.png)


---

5. **로그인 기능**
![](https://velog.velcdn.com/images/hhss2259/post/e29dfa5a-9e07-4419-9ae4-662101da0f65/image.png)


---

6. **회원가입 기능**
![](https://velog.velcdn.com/images/hhss2259/post/c1fa1f52-5f7c-49d0-8ead-367d37b76909/image.png)


---

## 👨‍🔧 프로젝트 세부 주제

---
### [개발 환경 구성하기]

1. **실패 사례 - 리액트 프로젝트를 jar 파일 안에 포함시키기**
* 리액트 프로젝트와 스프링 프로젝트를 동시에 빌드해  jar 파일 내부에 리액트 프로젝트를 포함시키는 방법
* 이 방법을 포기한 이유?
        1. build.gradle을 수정하는 작업이 꽤나 복잡하여 잘 작동하지 않았다
        2. 두 프로젝트를 동시에 빌드하다보니 너무 많은 시간과 비용이 든다
        3. 리액트와 스프링 프로젝트 둘 중 하나만 수정하더라도 전체 프로젝트를 다시 빌드, 실행해야되는 상황이 반복된다
<br/>

2. **대면 개발 환경 조성**
![](https://velog.velcdn.com/images/hhss2259/post/8d25919f-66b9-449f-a0c5-5af9e5b7e48e/image.png) 
* 대면으로 만나서 개발을 진행했기 때문에 같은 네트워크에 속한 상태에서 개발을 진행
* 사설 IP와 포트번호만을 이용해 서로의 React 서버와 Spring 서버를 연동
<br/>

3. **원격 개발 환경 조성**
    
![](https://velog.velcdn.com/images/hhss2259/post/7267401a-29d6-4f96-8ab6-d3e87ee94ef6/image.png)
- 프론트엔드 개발자가 항시 백엔드 서버에 접근할 수 있도록 EC2 인스턴스에 임시로 백엔드 서버를 배포
- 배포 방식은 FileZilla 프로그램을 이용해 EC2 인스턴스에 jar 파일을 전송하고 jar 파일을 실행
- HeidiSql 프로그램을 통해 AWS RDS 인스턴스의 내부의 데이터 접근 및 관리
<br/>

4. **Docker를 통한 개발 환경 조성**
    
![](https://velog.velcdn.com/images/hhss2259/post/cf8f7fc9-e321-4218-bf54-5d5caa2e9175/image.png)  
- 도커를 도입해 단 한 번의 Dockerfile 작성을 통해 **어디든 실행가능한 형태로 웹 어플리케이션을 구축**
- 도커 사용법을 숙지한다면 프론트엔드 개발자이든 백엔드 개발자이든 환경 설정 및 실행에 부담감 없이 어플리케이션을 실행하고 테스트
<br/>


### [DB 연동하기]

개발 진행하는 과정에서 총 세 가지 타입의 DB를 사용

1. **개발 시**에는 **Local 에 설치한 DB**를 사용
2. **실제 배포 시**에는 **AWS의 RDS 인스턴스**를 사용
3. **도커 테스트**를 위해 **도커 컨테이너로 실행한 DB**를 사용

application-db.yml

```java
#defaul 설정(공통)
spring:
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        dialect: org.hibernate.dialect.MariaDBDialect

logging.level:
  org.hibernate.sql: debug

--- #dev 설정(개발 환경)
spring:
  config:
    activate:
      on-profile: "db-local"

  datasource:
    url: jdbc:mariadb://로컬 호스트:3306/firesea
    username: 관리자 아이디
    password: 관리자 password
    driver-class-name: org.mariadb.jdbc.Driver

--- # production 설정(실제 운영 환경)
spring:
  config:
    activate:
      on-profile: "db-aws"

  datasource:
    url: jdbc:mariadb://RDS 엔드포인트/fireseadb
    username: 관리자 아이디
    password: 관리자 password
    driver-class-name: org.mariadb.jdbc.Driver

--- #dev 설정(개발 환경 - docker 사용)
spring:
  config:
    activate:
      on-profile: "db-docker-local"

  datasource:
    url: jdbc:mariadb://도커 컨테이너 ip:3306/firesea
    username: 관리자 아이디
    password: 관리자 password
    driver-class-name: org.mariadb.jdbc.Driver

```

### [JWT를 사용한 로그인 과정]

**직접 작성한 JWT 가이드**

![](https://velog.velcdn.com/images/hhss2259/post/4a3c154a-fcfd-4377-aee1-399b8e8d30c5/image.png)


**JWT 토큰 생성 및 발급 과정**

![](https://velog.velcdn.com/images/hhss2259/post/718dcb07-97e1-4637-a8dd-fdc1a9c414f8/image.png)


**JWT 토큰 재발급 과정**

![](https://velog.velcdn.com/images/hhss2259/post/2d69f394-5c09-4874-a163-c80168687621/image.png)


**구현 클래스**

- **JwtConstants** - 필요한 상수 값(암호화 키 및 토큰 유효 기간 등)들을 정리해 놓은 클래스
- **JwtFactory** - 실제 토큰을 만드는 과정을 전담
- **JwtAuthorizationInterceptor** - 해당 요청을 Controller로 넘기기 전 AccessToken의 유효성을 판단하는 Interceptor
- **MemberInfoController** - 로그인을 진행하는 Controller, 로그인에 성공하면 AccessToken과 RefreshToken을 발급한다
- **RefreshTokenControlle**r - ****RefreshToken 처리 요청을 담당하는 Controller
- **TokenService** - RefreshToken의 실제 유효성을 검증해주는 Service

### [JPA, Querydsl 사용 예시 - 검색 기능 만들기]

구현하고자 하는 검색 조건

1. 검색 내용을 **‘제목+내용’** 혹은 **‘작성자 닉네임’** 중 하나로 선택
2. 여러 개의 검색 키워드가 있는 경우 **모든 검색 키워드를 포함**하고 있는 글 목록을 뽑아낸다. 
3. **검색 키워드를 공백을 포함한 특수 문자로 분리**해내고 분리된 단어들을 모두 포함하고 있는 글들을 검색
4. 검색한 목록들이 다수인 경우 **페이징이 가능해야** 한다

**Querydsl 사용 시 검색 조건 생성하기** 

```java
// 해당 BooleanExpression은 Querydsl 코드에 전달하면 
// Querydsl이 해당 표현식을 사용하는 쿼리를 작성해준다
private BooleanExpression **checkContent**(String content) { //'제목+내용'으로 검색

				//검색 키워드를 **공백과 특수문자로 분리**해낸다
				//Ex) '이 노래 좋다'=> '이','노래',"좋다"
        String[] words = content.split("[^\uAC00-\uD7A3xfe0-9a-zA-Z)]");

				//1. 글의 제목과 내용을 **하나의 긴 글로 연결**짓고
	      //2. **첫번째 검색 단어를 포함하고 있는 글을 찾는 조건**을 만들어낸다.  
				BooleanExpression contains = textMessage
																			.textTitle
																			.concat(textMessage.textBody)
																			.contains(words[0]);

        if (words.length == 1) { 
						// 길이가 1 => 검색하는 단어가 한 개인 경우
						// 더 이상 추가할 조건이 없으므로 검색 조건을 그대로 return 한다. 
            return contains;
        } else { 
						// 길이가 2 이상 => 검색하는 단어가 여러 개인 경우
						//조건에 추가 조건을 더 붙인다=> 검색 단어들을 추가한다.
            for (int i = 1; i<words.length ; i++){
                contains = contains 
														.and(textMessage.textTitle.concat(textMessage.textBody)
														.contains(words[i]));
            }
        }
        return contains;
   }
```

### [배포 과정]

**도커를 사용하지 않는 경우**

1. 스프링과  리액트를 같이 빌드해서 배포하기
2. 스프링 따로, 리액트 따로 빌드해서 배포하기

**도커를 사용하는 경우** 

1. **Git과 Docker-Compse를 이용해서 배포하기**

![](https://velog.velcdn.com/images/hhss2259/post/ab038507-b577-4e17-a0c4-72e4b647fbd7/image.png)


전체 프로젝트와 Docker-Compose를 GiHub에 업로드

⇒ EC2 인스턴스에서 전체 프로젝트를 클론해 온 후 Docker-Compose를 통해 직접 이미지를 빌드하고 실행

**[단점]**

- yml 같은 기밀 파일들을 깃허브에 업로드 할 수 없다. 기밀 파일을 다른 방법을 통해 따로 관리해주어야 한다.
- 프로젝트 사이즈가 점점 커지면서 EC2 인스턴스에서 프로젝트를 빌드하는 것 자체가 불가능해졌다. CPU 사용률이 98%까지 치솟으면서 서버가 부하에 걸려 다운되는 현상이 반복

1. **Docker-Hub에 Docker-Image를 업로드해서 배포하기 (최종 배포 방법으로 채택)**

![](https://velog.velcdn.com/images/hhss2259/post/5d36acd1-cc6a-4f3d-a045-e6bc644e8a0e/image.png)


Local에서 먼저 리액트, 스프링, Nginx 프로젝트를 각각 도커 이미지로 빌드한 후 DockerHub에 업로드

⇒ EC2 인스턴스에서 Docker-Hub를 통해 필요한 각 이미지들을 pull 해온 후 직접 실행

⇒ 향후 Docker-Compose를 새롭게 작성해서 필요한 이미지들을 동시에 pull 해 오고 실행할 수 있을 것이라고 판단

⇒ 최종 배포 과정으로 선택하였다

**도커를 사용한 이유는 무엇인가?**

1. 백엔드 개발자로서 다양한 어플리케이션(nginx, spring, react)의 실행 환경을 하나하나 다 설정하는 과정이 부담스럽고 또 복잡했다**.** 
2. EC2 인스턴스 삭제했다가 다시 시작하는 경우 매번 복잡한 실행 환경을 다시 설정해주어야 했다.

### 🧐 **추가적으로 구현해야 할 사항(현재 미개발)**

---

1. SNS 로그인을 실제 배포 시에도 사용 가능하도록 만들기
2. AWS 상에서 실제 scale-out을 적용해보기
    - Nginx를 통한 로드밸런싱
    - AWS 로드밸런서 사용하기
3. 젠킨스를 통한 CI/CD 과정 학습 후 적용하기
