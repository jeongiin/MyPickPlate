package com.example.my_firstpractice.Kotlin

// 02. 자료형
// 정수형 -> Long > Int > Short > Byte
// 실수형 -> Double > Float
// 문자 -> Char
// 문자열 -> String
// 논리형(참True/거짓False) -> Boolean

// 변수 선언하는 방법(1)
// Variable/Value 변수명 = 값
// Var/val 변수명 = 값
var number = 10

// 변수 선언하는 방법(2)
// Var/val 변수 : 자료형 = 값

var number1 : Int = 20
var hello1 : String = "Hello"
var point1 : Double = 5.6

// Variable or Value?
// - 1. 변하지 않는 값이라면 Value
// - 2. 진짜 모르겠다 -> Value (바꿀 일이 생기면 그 때 가서 Variable로 선언)

fun main(array: Array<String>){
    number = 20
//    number = 20.5

}