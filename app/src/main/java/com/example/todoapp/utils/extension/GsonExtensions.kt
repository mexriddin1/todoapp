package com.example.core.extension

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


private val gson = GsonBuilder()
    .create()

fun <T> String.parseTo(): T {
    val type = object : TypeToken<T>() {}.type
    return gson.fromJson<T>(this, type)
}

fun <T> T.toJson(): String {
    return gson.toJson(this)
}