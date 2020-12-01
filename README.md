# **오늘의 식사 My Pick Plate**
<a href='https://ifh.cc/v-Y9oYtJ' target='_blank'><img src='https://ifh.cc/g/Y9oYtJ.jpg' width="300" height="300" border='0'></a>
---
<br><br>
## 소개글

### 음식 사진만으로 메뉴를 분류하는 인공지능 기술을 활용하여 키보드를 없앤 **맛집 추천 애플리케이션**

- 내가 찍은 음식 사진, 내가 먹고 싶은 음식 사진으로 음식을 선택만 하면 자동 분류 !
- 자동으로 분류된 음식 사진을 애플리케이션 내에 저장 가능 !
- 내 위치를 기반으로 맛집 리스트 추천 가능 !
- 내 위치와 맛집 위치를 지도로 확인 가능 !
<br>
<a href='https://ifh.cc/v-kl4Ny2' target='_blank'><img src='https://ifh.cc/g/kl4Ny2.jpg' border='0'></a>

### 주요 기능

 1. 음식 사진 불러오기
 2. 불러온 음식 사진 저장 및 삭제하기
 3. 음식 사진으로 음식 분류하기
 4. 저장한 음식 사진 모아보기
 5. 분류한 음식, 내 위치 기반 맛집 추천하기
 6. 내 위치와 맛집 위치 지도로 보여주기

<br><br>
## Process
<a href='https://ifh.cc/v-PP09nv' target='_blank'><img src='https://ifh.cc/g/PP09nv.jpg' border='0'></a>
  
### 모델 개발
- 텐스플로우, 케라스, 텐서플로우 라이트
  * 언어 : Python
  
- 데이터
  * 한국 갤럽 등 한국인, 외국인에게 인기있는 음식 조사
  * 음식과 관련 없는 사진, 글씨가 포함된 사진, 음식 사이즈가 너무 작은 사진, 너무 많은 음식들과 함께 찍힌 사진 등 적합하지 않은 데이터를 삭제
  
- Network
  * MobileNet V2, EfficientNet B0, InceptionNet V3 의 Pre-trained model을 사용한 Transfer learning
  * Hyperparameter tuning
  * 20개 class에 대한 모델 학습 및 테스트 결과
  * 100개 class에 대한 모델 학습 및 테스트 결과
  
<br> 

### 프론트엔드
- 안드로이드
  * 언어: Kotlin
  * 사용 기술: Okhttp, RecyclerView, Json
  
<br>

### 버전관리
- Git

<br>

### 이슈 및 협업 일정 관리
- Git

<br><br>

## 팀원

🙍‍♀️이정인  
[@jeongin](https://github.com/jeongiin)
- 안드로이드
- UI 디자인 설계
- 모델 개발

<br>

🙍‍♀️염지현  
[@yeomja99](https://github.com/yeomja99)
- 안드로이드
- 데이터 전처리
- 앱 테스트 영상 촬영



