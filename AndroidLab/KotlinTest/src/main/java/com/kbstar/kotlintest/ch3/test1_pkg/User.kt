package com.kbstar.kotlintest.ch3.test1_pkg

// 동일 패키지에 있지 않은 다른 패키지의 멤버(클래스, 변수, 함수) 사용하려면.. import로 선언해서 사용해라
import java.text.SimpleDateFormat
import java.util.Date

/*
    코틀린에서는 변수 함수가 꼭 클래스로 묶이지 않아도 무관함
    top level에 선언도 가능하다!
 */

// 클래스 없이 top level에 선언
var data = 10   // var(variable) : mutable   -> val을 기본으로 쓰고, 필요에 의해 var을 사용하는 것이 권장사항!
fun formatDate(date: Date) : String {
    val sdformat = SimpleDateFormat("yyyy-MM-dd")   // val(value) : immutable(final)
    return sdformat.format(date)
}

class User {
    var name = "Kildong Hong";
    fun sayHello() {
        println("hello $name")
    }
}