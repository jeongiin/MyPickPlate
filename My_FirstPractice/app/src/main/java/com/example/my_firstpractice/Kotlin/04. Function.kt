package com.example.my_firstpractice.Kotlin

// 04. Function
// -함수란
//      - 어떤 input을 넣어주면 어떤 output이 나오는 것
//      - y = x + 2

// - 함수를 선언하는 방법
// fun 함수명 (변수명:타입, 변수명:타입 .....) : 반환형 {
//      함수 내용
//      return 반환값
//}

fun plus(first: Int, second: Int): Int {
    println(first)
    println(second)
    val result = first + second
    println(result)
    return result
}

// 드래그하고 Ctrl + Shift + F10 -> 주석 처리
// Ctrl + Alt + L -> line 정리

// - 디폴트 값(기본값 : 특정한 값을 전달하지 않을 때 디폴트 값을 사용하겠다)을 갖는 함수 만들기
fun plusFive(first: Int, second: Int = 5): Int {
    val result = first + second
    return result
}

// - 반환값이 없는 함수 만들기
fun printPlus(first: Int, second: Int): Unit {
    val result: Int = first + second
    println(result)
}

fun printPlus2(first: Int, second: Int) { //Unit 생략 가능
    val result: Int = first + second
    println(result)
}

// -간단하게 함수를 선언하는 방법
fun plusShort(first: Int, second: Int) = first + second

// -가변인자를 갖는 함수 선언하는 방법
fun plusMany(vararg numbers: Int) {
    for (number in numbers) {
        println(number)
    }
}

fun main(array: Array<String>) {
    // 함수 호출하는 방법
    val result = plus(5, 10)
    println(result)
    // 인수를 명시적으로 전달하는 방법
    val result2 = plus(first = 20, second = 3)
    println(result2)

    val result3 = plus(second = 100, first = 10)
    println(result3)

    // 디폴트 값을 갖는 함수 호출하기
    println()
    val result4 = plusFive(10, 20)
    println(result4)
    val result5 = plusFive(10)
    println(result5)

    println()
    printPlus(10, 20)

    println()
    val result6 = plusShort(50, 50)
    println(result6)

    println()
    plusMany(1, 2, 3)
    plusMany(100)

}