package com.example.my_firstpractice.Kotlin

fun main(args: Array<String>) {

    val a: Int? = null
    val b: Int = 10
    val c: Int = 100

    if (a == null) {
        println("a is null")
    } else {
        println("a is not null")
    }

    if (b + c == 110) {
        println("b plus c is 110")
    } else {
        println("b plus c is not 110")
    }

    // 엘비스 연산자 -> null에 대응하기 위한 문법적인 요소
    val number: Int? = null
    val number2: Int = number ?: 10 //number2에 number가 들어가는데 이때 number가 null일 경우 10이 들어감
    println()
    println(number2)

    // 밑에 if문은 무조건 리턴값을 반환하기 때문에 기다 아니다 여야 되는데 (num1<num2)의 경우에 리턴하는 값이 없음
    // 그렇기 때문에 모든 상황을 고려하기 위해 else 문을 반드시 사용
    val num1: Int = 10
    val num2: Int = 20
    val max = if (num1>num2) {
        num1
    } else {
        num2
    }

}