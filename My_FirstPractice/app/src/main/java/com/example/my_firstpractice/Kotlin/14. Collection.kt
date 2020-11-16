package com.example.my_firstpractice.Kotlin

// 14. Collection
// -> list, set, map

fun main(array: Array<String>) {

    // List -> 중복 혀용 (o)
    val numberList = listOf<Int>(1, 2, 3, 3, 3)
    println(numberList)
    println(numberList.get(0))
    println(numberList[0])

    // Set -> 중복 허용 (x)
    //     -> 순서가 없다!!(index가 없다)
    val numberSet = setOf<Int>(1, 2, 3, 3, 3)
    println()
    numberSet.forEach{
        println(it)
    }

    // Map -> Key, value 방식으로 관리한다
    val numberMap=mapOf<String, Int>("one" to 1,"two" to 2)
    println()
    println(numberMap.get("one"))

}
