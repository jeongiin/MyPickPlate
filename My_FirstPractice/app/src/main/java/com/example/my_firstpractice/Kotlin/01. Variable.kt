package com.example.my_firstpractice.Kotlin




// 01. Variable -> 변수
// -> 상자
//         - 내 마음대로 원하는 것을 넣을 수 있는 상자 -> Variable
//         - 한 번 넣으면 바꿀 수 없는 상자 -> Value

// 변수 선언하는 방법
// var/val 변수명(상자) = 값(넣고 싶은 것)
// Variable/Value

var num = 10
var hello = "hello"
var point = 3.4

val fix = 20
fun main(args:Array<String>){

    println(num)
    println(hello)
    println(point)
    println(fix)

    // 엄청 긴 코드
    // num의 값을 변경했나 안했나...? -> Value로 선언할 경우 헷갈리지 않고 변경 가능

    num = 20
    hello = "Good Bye"
    point=10.4

    println(num)
    println(hello)
    println(point)
    println(fix)

    //fix = 500 -> 변경불가

}