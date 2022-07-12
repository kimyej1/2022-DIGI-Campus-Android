/*
    코틀린에서는 .kt 파일이 있는 위치와 다른 가상의 패키지명을 줄 수 있다.
    실제 컴파일된 .class 파일이 패키지 위치에 들어가게 된다.
 */
package com.kbstar.kotlintest.ch3.test1_pkg.one

import com.kbstar.kotlintest.ch3.test1_pkg.User
import com.kbstar.kotlintest.ch3.test1_pkg.data
import com.kbstar.kotlintest.ch3.test1_pkg.formatDate
import java.util.*

fun main() {
    // 동일 패키지라서 import 불필요
    data = 20
    formatDate(Date())

    println("2...")
    val obj = Test2()
    println(obj.e)
    println("3...")
}

class Test2 {
    var a : Int = 0;
    lateinit var b : String;    // lateinit으로 초기화 시점을 미룰 수 있다.
    var c : Boolean = false;
//    lateinit var d : Double;  // 그러나 기초 데이터타입에는 쓸 수 없다.

//    var obj : User;           // 초기화 안했기때문에 오류
    lateinit var obj : User;    // 기초 데이터타입이 아니기 때문에 lateinit 가능

    val e : Int by lazy {       // by lazy{} : 초기화 시점을 미루겠다, 실제 처음 사용될때까지..
        println("1...")
        10
    }
}