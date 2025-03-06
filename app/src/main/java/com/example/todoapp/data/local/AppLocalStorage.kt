package com.example.todoapp.data.local

import android.content.Context
import com.example.core.extension.SharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLocalStorage @Inject constructor(@ApplicationContext context: Context) :
    SharedPreference(context) {

    var time: Long by longs()
}