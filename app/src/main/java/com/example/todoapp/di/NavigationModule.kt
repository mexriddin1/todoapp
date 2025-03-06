package com.example.todoapp.di

import com.example.todoapp.utils.navigation.AppNavigator
import com.example.todoapp.utils.navigation.AppNavigatorDispatcher
import com.example.todoapp.utils.navigation.AppNavigatorHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    fun bindAppNavigationHandler(impl: AppNavigatorDispatcher): AppNavigatorHandler

    @Binds
    fun bindAppNavigation(impl: AppNavigatorDispatcher): AppNavigator
}