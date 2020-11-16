package com.example.my_firstpractice.Kotlin

// 11. 제어 흐름 실습

fun main(args: Array<String>) {

    val value: Int? = null

    when (value) {
        null -> println("value is null")
        else -> println("vlaue is not null")
    }

    val value2: Boolean? = null

    // when 구문은 조건으로 갖는 값의 모든 경우에 대응 해주는 것이 좋다
    when (value2) {
        true -> println("")
        false -> println("")
        //모든 경우의 수에 대비하여 코드 작성
        null -> println("")
    }

    // 값을 리턴하는 when 구문의 경우 반드시 값을 리턴해야 한다
    val value3 = when (value2) {  //value2는 null을 가질 수 있는 boolean 따라서 null 조건도 추가
        true -> 1
        false -> 3
        null -> 4 // else -> 4
    }

    // when의 다양한 조건식
    val value4: Int = 10
    when (value4) {
        //is -> type을 물을 수 있는 명령어
        is Int -> {
            println("value is int")
        }
        else -> {
            println("value is not int")

        }
    }

    val value5: Int = 60
    when (value5) {
        //..의 범위는?
        in 60..70 -> println("value is in 60-70")
        in 70..80 -> println("value is in 70-80")
        in 80..90 -> println("value is in 80-90")

    }
}