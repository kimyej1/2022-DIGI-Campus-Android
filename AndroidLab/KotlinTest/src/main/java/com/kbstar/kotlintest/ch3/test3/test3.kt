package com.kbstar.kotlintest.ch3.test3

open class Super(no: Int) {
    constructor(no: Int, name: String) : this(no) {
        println("Super.. constructor")
    }

    init {
        println("Super.. init")
    }
}

class Sub(no: Int) : Super(no, "kkang") {
    constructor(no: Int, name: String) : this(no) {
        println("Sub.. constructor")
    }

    init {
        println("Sub.. init")
    }
}

fun main() {
    Sub(10, "kim")
}