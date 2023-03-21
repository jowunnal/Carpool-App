# Carpool App

# 소개

Carpool App은 대학생들을 대상으로 차량을 공유하여 드라이버는 수익을 얻고, 패신저는 편하고 빠르게 등교할 수 있도록 차량을 공유할 수 있는 매칭 서비스를 제공하는 앱 입니다.

# 주요 기능

- 로그인, 로그아웃, 회원가입, 회원탈퇴
- 사용자는 패신저/드라이버 로 구분합니다.
- 패신저는 카풀을 예약, 예약취소, 드라이버로 등록, 승객 혹은 드라이버를 신고할 수 있습니다.
- 드라이버는 카풀을 생성, 수정, 삭제, 승객을 퇴출, 신고할 수 있습니다.

# 사용된 스택

- Compose
- Kotlin.Coroutines.Flow
- Databinding
- Navigation ( Single Activity )
- DataStore
- Hilt
- Retrofit2 & Okhttp3

# Architecture ( MVVM )

 - Data
    - DataSource
 	  - DataStore
  	  - Serializer
    - Repository Interface
    - Api Interface
    - DTO
      - Request
      - Response

 - Domain
  	- Repository Implementation
  	- Model

 - Presenter
    - Base
      - BaseFragment
      - BaseViewModel
      - BaseComposeFragment
      - BaseBottomSheetDialogFragment
  	- Activity & Fragment
   		- BindingAdapter
   		- State
  	- Compose Component
   		- State
      
 - Di
    - RepositoryModule
    - RetrofitModule
    - DataStoreModule

 - Utils

# 배운점

- 기획 > 디자인 > Meeting > Correction > 개발 > QA 까지의 개발 프로세스를 학습했습니다.
- 협업을 통한 의사소통의 중요성 과 어떻게 해야 할지를 배웠습니다.
- 코드의 가독성을 높이기 위해 IDEA 에서 제공하는 code formatting 기능을 이용하고, 팀원과의 약속을 정의하는 방법을 배웠습니다.
- JWT 토큰을 서버로 부터 받아 원하는 데이터를 요청하고, 응답 받아 가공하여 view에서 원하는 데이터만 화면에 보여주는 형태를 배웠습니다.
- 명령형 UI paradigm의 databinding 과 선언형 UI paradigm의 State Hoisting
- kotlin.coroutines.flow 를 활용한 비동기 프로그래밍
- 다양한 JetPack-Navigation 의 활용과 backStack 관리 (layout 방식의 Navigation 과 Compose-Navigation 함께 사용하여 Argument 전달하기)
- figma에 정의된 공통 컴포넌트들을 정의하고 재사용하며, 기능들을 모듈화하여 재사용

# 스택 변화

 - 명령형 -> Compose(선언형)
 - LiveData -> Flow
 - SharedPreferences -> DataStore ( proto )

# UI

![홈](https://user-images.githubusercontent.com/75519689/226547890-d8698695-4455-40cb-a4b6-634de973756f.jpg)
홈화면 일때
<br/>
![내티켓](https://user-images.githubusercontent.com/75519689/226547936-1ad22eeb-c1d6-4edc-b8fc-05a3f596bb5f.jpg)
내 티켓 일때
<br/>
![다른티켓](https://user-images.githubusercontent.com/75519689/226547957-633c8748-f6d5-47d0-97f7-e37ac5f495ee.jpg)
내 티켓이 아닐때

# 동작 영상

https://user-images.githubusercontent.com/75519689/218297123-a64b2985-993a-4a82-9982-29853340b85c.mp4

# Member

| 기획 | 디자인 | BE | FE/AOS | FE/IOS |
| ----- | ----- | ----- | ----- | ----- |
| 2 | 2 | 2 | 2 | 1 |

# Detail

자세한 설명은 [깃블로그](https://jowunnal.github.io/projects/carpool/ "link") 에 있습니다.
