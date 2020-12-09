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
  
- [데이터](https://github.com/jeongiin/MyPickPlate/blob/dev/FoodClass/food_class.xlsx)
  * 한국 갤럽 등 한국인, 외국인에게 인기있는 음식 조사
  * 크롤링 코드를 통해 구글에서 음식을 키워드로 각각 1200장 이상의 이미지를 모은 후 전처리를 통해 300~500장의 이미지를 선정
  * 전처리 과정에서 음식과 관련 없는 사진, 글씨가 포함된 사진, 음식 사이즈가 너무 작은 사진, 너무 많은 음식들과 함께 찍힌 사진 등 적합하지 않은 데이터를 삭제
  
- Network
  * MobileNet V2, EfficientNet B0, InceptionNet V3 의 Pre-trained model을 사용한 Transfer learning
  * Hyperparameter tuning
  * [EfficientNet 전이학습 및 LOSS 그래프](https://github.com/jeongiin/MyPickPlate/blob/main/KoreaFoodClassification/TransferLearning_Efficient00.ipynb)
  * [InceptionNet V3 전이학습 및 LOSS 그래프](https://github.com/jeongiin/MyPickPlate/blob/main/KoreaFoodClassification/TransferLearning_InceptionV3.ipynb)
  * [20개 class에 대한 모델 학습 진행 과정](https://github.com/jeongiin/MyPickPlate/blob/dev/KoreaFoodClassification/TransferLearning_MobileNetV2_final_data20.ipynb)
  * [100개 class에 대한 모델 학습 진행 과정](https://github.com/jeongiin/MyPickPlate/blob/dev/KoreaFoodClassification/TransferLearning_MobileNetV2_final_data100.ipynb)
  
<br> 

### 프론트엔드
- 안드로이드
  * 언어: Kotlin
  * 사용 기술: Okhttp, RecyclerView, Json
  
- 핵심 기능
  * [음식 분류](https://github.com/jeongiin/MyPickPlate/blob/main/MyPickPlates/app/src/main/java/com/example/myapplication/view/UploadFoodActivity.kt)
  * [맛집 추천](https://github.com/jeongiin/MyPickPlate/blob/main/MyPickPlates/app/src/main/java/com/example/myapplication/view/RecommendFoodActivity.kt)
   - API : [네이버 지역 API](https://developers.naver.com/docs/search/local/)
   
<br>

### Usage
0) 아직 구글 플레이 스토어 등록 전이라 원하시는 분들은 아래 과정을 진행하시면 됩니다! 전시를 했다면 애뮬레이터로 직접 이용 가능하셨을텐데 아쉽습니다.
1) Android Studio 설치(4.0.1)
2) 갤럭시 개발자 모드 설정
3) github clone
4) API Developer 용 key 설정
5) Android Studio 애뮬레이터 실행

<br> 


### 버전관리
- Git 0.9

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



