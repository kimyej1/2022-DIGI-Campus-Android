package com.kbstar.androidlab


data class User(val name:String, val phone:String, val role:String) {

    companion object {
        val ADMIN = "ADMIN"
        val USER = "USER"
    }
}