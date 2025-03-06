package com.example.todoapp.domain.usecase

import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData
import com.example.todoapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UseCaseImpl @Inject constructor(
    private val repository: Repository
) : UseCase {
    override fun getAllTask(): Flow<Result<List<TaskUiData>>> = flow {
        emit(repository.getAllTask())
    }.catch { emit(Result.failure(Exception(it))) }

    override fun getTaskCount(): Flow<Result<List<Int>>> = flow {
        emit(repository.getTaskCount())
    }.catch { emit(Result.failure(Exception(it))) }

    override fun addNewTask(data: TaskUiData): Flow<Result<Unit>> = flow {
        emit(repository.addNewTask(data))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun editTask(data: Task): Flow<Result<Unit>> = flow {
        emit(repository.editTask(data))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun editRoot(data: TaskUiData): Flow<Result<Unit>> = flow {
        emit(repository.editRoot(data))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun editTaskItem(data: TaskItem): Flow<Result<Unit>> = flow {
        emit(repository.editTaskItem(data))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun loadItem(data: Int): Flow<Result<TaskUiData>> = flow {
        emit(repository.loadItem(data))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun deleteTaskItem(id: Int): Flow<Result<Unit>> = flow {
        emit(repository.deleteTaskItem(id))
    }.catch { emit(Result.failure(Exception(it))) }

    override fun deleteTask(id: Int): Flow<Result<Unit>> = flow {
        emit(repository.deleteTask(id))
    }.catch { emit(Result.failure(Exception(it))) }

}