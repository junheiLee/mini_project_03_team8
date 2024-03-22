
## 목차
- [프로젝트 정보](#-레거시-프로젝트)
- [구조](#-구조)
- [주요 관심사](#-주요-관심사)

# 🚀 레거시 프로젝트
[기존 프로젝트 보기](https://github.com/junheiLee/mini_project_03)
## 🔧 사용 기술 및 개발 환경
&nbsp; Eclipse, Java 11, oracle, JSP
## 🔍 소개
&nbsp; 부트캠프 세번째 미니 프로젝트로 쇼핑몰 사이트를 고도화하는 과제입니다.<br>
### 기간
&nbsp; 5일: 23. 8. 4(금) ~ 23. 8. 8(화)
### 인원
&nbsp; 김태섭, 이준희, 임승연, 정성현

# 🏗 구조
## Model1 방식에서 Model2 방식으로 변경
### [🔗기존 코드](https://github.com/junheiLee/mini_project_03)
1. 모든 요청은 `NonageServlet`과 `ActionFactory`를 거쳐 처리함.
   1. *"/NonageServlet"* 경로를 `NonageServlet`에 매핑
   2. 각 요청을 *command 파라미터*로 구분
   3. `ActionFactory`에서 *command 파라미터*에 대한 `Action` 반환

2. 각 `Action`은 해당 request, response, 필요한 DAO에 접근해 요청을 처리

### 1. `NonageServlet`과 `ActionFactory`의 결합 및 관심사 분할
&nbsp; 요청의 관심사에 따라 `Controller` 생성<span style="color: #808080">(Cart, Item, Member, Order, Qna, Admin)</span>
- 각 `Controller`에 요청의 관심사를 매핑<span style="color: #808080">(NonageServlet 책임 분할)</span>
- 해당 관심사에 대한 처리는 해당 `Controller`가 분배<span style="color: #808080">(결합 및 ActionFactory 책임 분할)</span>
   - getPathInfo() 함수 이용해 파라미터가 아닌 경로로 action을 확인

### 2. `Action`의 책임 분리
&nbsp; Service 계층 추가
- HttpServlet에 종속되지 않는 비즈니스 로직을 분리해 service 계층에 할당
- `Controller`에서 받은 data로 로직 수행
- DAO는 Service 계층에서만 접근


# 😍 주요 관심사
- 함께 성장하기

## 🤩느낀 점
- 제공해주신 코드를 분석하는 것이 어려웠습니다.
- 다른 사람에게 가르쳐 주기 위해서는 더 깊은 이해가 필요함을 깨달았습니다.
- 기존 코드에서 조금씩 고치는 것이 좋을 것 같다고 생각했습니다.
- 반복되는 코드가 많아 불편했습니다.
