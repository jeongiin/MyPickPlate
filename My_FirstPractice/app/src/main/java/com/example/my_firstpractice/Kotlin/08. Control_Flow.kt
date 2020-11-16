package com.example.my_firstpractice.Kotlin

// 08. 제어 흐름
// - if, else


fun main(array: Array<String>) {
    val a: Int = 5
    val b: Int = 10

    // if/else 사용하는 방법 (1)
    if (a > b) {
        println("a가 b보다 크다")
    } else {
        println("a가 b보다 작다")
    }
    println("3번")
    // if/else/else if 사용하는 방법 (2)
    if (a > b) {
        println("a가 b보다 크다")
    } else if (a < b) {
        println("a가 b보다 작다")
    } else if (a==b){
        println("a와 b는 같다")
    }else{

    }

    // 값을 return 하는 if 사용방법
    val max = if (a>b){
        a
    } else{
        b
    }

//    val max = if (a>b) a else b

    println()
    println(max)
}