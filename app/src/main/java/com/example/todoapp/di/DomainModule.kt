package com.example.todoapp.di

import com.example.todoapp.domain.repository.Repository
import com.example.todoapp.domain.repository.RepositoryImpl
import com.example.todoapp.domain.usecase.UseCase
import com.example.todoapp.domain.usecase.UseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    @Binds
    fun bindUseCase(impl: UseCaseImpl): UseCase
}