package com.kbstar.kotlintest.ch3.test3

data class User(val no: Int, val name: String)

open class Super2 {
    open fun some(){}
}

class Outer {
    var data1 = 10

    val obj = object : Super2() {   // object : 선언과 동시에 객체 생성
        override fun some() {

        }
    }

    companion object {  // = java "static"
        var data2 = 20
        fun sayHello() {}
    }
}

fun main() {
    val user1 = User(10, "kim")
    val user2 = User(10, "kim")

    println("${user1.equals(user2)}")   // class 앞에 data 안붙으면 false
    println(user1.toString())
    println(user2.toString())

//    Outer.data1++   // 객체 생성 후 사용해야하는데 그냥 바로 쓰면 안됨
    Outer().data1++   // OK
    Outer.data2       // Companion object 쓰면 바로도 사용 가능
    Outer.sayHello()
//    Outer().data2++   // 객체로는 접근이 안된다.. -> 클래스명으로만 접근 가능!!
}