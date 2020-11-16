package com.example.my_firstpractice.Kotlin

var a = 1 + 2 + 3 + 4 + 5 // 연산의 결과값을 변수에 넣어줄 수 있다
var b = "1" // String
var c = b.toInt()  //String -> Int
var d = b.toFloat()  //String -> Float

var e = "John"
var f = "My name is $e Nice to meet you" //$뒤에 변수 추가

// Null
// - 존재하지 않는다
//var abc : Int = null //abc는 Int형만 받을 수 있으므로 null은 받을 수 없다
var abc1 : Int? = null // 자료형 뒤에 "?"를 적을 경우 null을 가질 수 있음
var abc2 : Double? = null // "null" (x) / 존재하지 않음 (o)

var g = a + 3 // 18, 결과값만 지정해주면 된다

fun main(array : Array<String>){
    println(a)
    println(b)
    println(c)
    println(d)
    println(e)
    println(f)
    println(abc1)

    println(g)
}