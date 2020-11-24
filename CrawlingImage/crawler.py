
from urllib.request import urlretrieve
from urllib.parse import quote_plus
from bs4 import  BeautifulSoup
from selenium import webdriver
from selenium.webdriver import Chrome
from selenium.webdriver.common.keys import Keys
import time

'''
1. chrondriver 미리 설치 + 경로 기억
2. search key word & name 사전 지정
3. folder 구성 (search 네임으로 된 폴더와 train, test, val, ect 폴더 필요)
'''
search = '깐풍기' # 갈비, 김치찌개, 깐풍기, 꿔바로우, 된장찌개, 떡볶이, 마라탕, 볶음밥, 부대찌개, 불고기, 비빔밥, 삼겹살, 순두부찌개, 우동, 유산슬, 잡채, 짜장면, 짬뽕, 청국장, 팔보채
name = 'kkanpung-gi' # galbi, gimchijjigae, kkanpung-gi, kkwobalou, doenjangjjigae, tteogbokki,  malatang, bokk-eumbab, budaejjigae, bulgogi, bibimbab, samgyeobsal, sundubujjigae, udong, yusanseul, jabchae, jjajangmyeon, jjamppong, cheong-gugjang, palbochae
url = 'https://www.google.com/search?q='+search+'&sxsrf=ALeKk03IxLUnCXWvf0FhItrtv52T9NRQ3Q:1604817220329&source=lnms&tbm=isch&sa=X&ved=2ahUKEwjsiuOCqvLsAhVSPnAKHRWyA6kQ_AUoAXoECBsQAw&biw=1522&bih=812'

driver = webdriver.Chrome("C:/Users/jeongin/chromedriver_win32 (1)/chromedriver.exe") #자신의 경로로 바꿔줘야함
# 크롬이 업데이트 되어 존재하던 크롬 드라이버 버전과 일치하지 않는 경우 에러가 발생할 수 있음

driver.get(url)

# google 이미지 데이터를 다량 수집하기 위한 꼼수
body = driver.find_element_by_tag_name('body')  # 스크롤하기 위한 소스

# range에 page 번호 작성 여러번 내려갈수록 많이 불러옴


for vindex in range(20):
    body.send_keys((Keys.END))
    time.sleep(1)
    try :
        driver.find_element_by_xpath("/html/body/div[2]/c-wiz/div[3]/div[1]/div/div/div/div/div[5]/input").click()
        print("결과 더보기 버튼을 찾았습니다.")
    except :
        print("결과 더보기 버튼을 찾을 수 없습니다.")




body.send_keys(Keys.HOME)  # 홈 키로 최상단
##############################################

html = driver.page_source
soup = BeautifulSoup(html, features="html.parser")

img = soup.find_all("img", class_ = "rg_i Q4LuWd")
#img = soup.select('.rg_i.Q4LuWd')

n=1
imgurl = []


for i in img:
    try:
        imgurl.append(i.attrs["src"])

    except KeyError:
        imgurl.append(i.attrs["data-src"])




# print(len(imgurl))
# train : val : test = 6 : 2: 2 로 맞추고자 함

for i in imgurl:
    if (n>400):
        # 현재 400장의 사진까지는 모았으므로 결과 더보기 버튼을 누른 뒤 사진을 추가적으로 크롤링
        urlretrieve(i, search + "/" + name + str(n) + ".jpg")
    n+=1

# for i in range(60):
#     try :

print(name + " crawling finish")


driver.close()