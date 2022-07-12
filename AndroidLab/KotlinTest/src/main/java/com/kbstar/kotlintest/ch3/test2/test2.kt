package com.kbstar.kotlintest.ch3.test2

// 코틀린은 모든게 객체.. primitive/reference 구분 X
var a : Int = 10
var d : Double = 10.0

fun some() {
    a.plus(10)
//    d = a             // 코틀린은 암시적 타입캐스팅 안됨
    d = a.toDouble()    // OK
}