package com.kbstar.kotlintest.ch3.test3

fun main() {
    fun ifTest(arg: Int) {
       val result = if(arg > 100) {
           println("arg > 100");
           100
       } else if(arg > 10) {
           println("arg > 10");
           arg
       } else {
           println("arg <= 10");
           0
       }
        println("result = $result");
    }
    ifTest(10);
    println("---------------")

    fun forTest() {
        for(i in 10 downTo 1 step 2) {
            println(i)
        }
        println("---------------")
        val array = arrayOf("hello", "world")
        for(a in array) {
            println(a)
        }
        println("---------------")
        for(a in array.indices) {
            println(a)
        }
        println("---------------")
        for ((a, b) in array.withIndex()) {
            println("$a, $b");
        }

        println("---------------")
        val map = mapOf<String, String>("one" to "hello", "two" to "world")
        for(item in map) {
            println("key : '${item.key}', value : '${item.value}'")
        }
    }
    forTest();
    println("---------------")

    fun whenTest(arg : Any) {
        when(arg) {
            1 -> println("1....")
            10, 20 -> println("10, 20....")
            30 -> {
                val result = arg as Int * 10    // as Int : int로 type-casting
                println("30....$result")
            }
            "hello" -> println("hello....")
            is Int -> println("Int...")
            else -> println("else...")
        }
    }

    whenTest(100)
}