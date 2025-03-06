package com.example.core.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow


@Deprecated("Interceptor is written instead")
//fun OkHttpClient.Builder.addContentHeaderIfNeeded(preference: AppLocalStorage): OkHttpClient.Builder {
//    addInterceptor { chain ->
//        val originalRequest = chain.request()
//
//        if (originalRequest.url.toString().contains("card")) {
//            val newRequest = originalRequest.newBuilder()
//                .addHeader("Authorization", "Bearer ${preference.accessToken}")
//                .build()
//
//            chain.proceed(newRequest)
//        } else {
//            chain.proceed(originalRequest)
//        }
//    }
//    return this
//}

fun <T> result(block: suspend () -> T): Flow<Result<T>> = flow {
    try {
        emit(Result.success(block.invoke()))
    } catch (e: Exception) {
        emit(Result.failure(e))
    }
}.catch { e ->
    emit(Result.failure(Exception(e)))
}






