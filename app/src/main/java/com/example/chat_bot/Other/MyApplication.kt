package com.example.chat_bot.Other


lateinit var user_name: String

class MyApplication {

    fun getUserName(): String {
        return user_name
    }

    fun setUserName(cart: String) {
        user_name = cart
    }
}