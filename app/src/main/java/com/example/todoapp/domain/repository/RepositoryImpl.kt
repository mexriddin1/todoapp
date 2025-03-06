package com.example.todoapp.domain.repository

import com.example.todoapp.core.Type
import com.example.todoapp.data.local.AppLocalStorage
import com.example.todoapp.data.local.room.dao.TaskDao
import com.example.todoapp.data.local.room.dao.TaskItemDao
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.data.local.room.entity.TaskUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val db: TaskDao,
    private val dbItem: TaskItemDao,
    private val appLocalStorage: AppLocalStorage
) : Repository {

    init {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTime

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val startOfDay = calendar.timeInMillis

        if (appLocalStorage.time < startOfDay) {
            clear()
        }
    }

    private fun clear() {
//        db.clearTask()
//        db.clearTaskItem()
    }

    override suspend fun getAllTask(): Result<List<TaskUiData>> = withContext(Dispatchers.IO) {
        try {
            val tasks = db.getAll()
            Result.success(tasks.map {
                TaskUiData(
                    id = it.id,
                    task = it.task,
                    type = it.type,
                    createTime = it.createTime,
                    alertTime = it.alertTime,
                    addedTime = it.startTime,
                    checked = it.checked,
                    list = db.getAllItemById(it.id)
                )
            })
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun getTaskCount(): Result<List<Int>> = withContext(Dispatchers.IO) {
        val list = mutableListOf(0, 0, 0, 0)
        try {
            val tasks = db.getAll()
            tasks.forEach {
                when (it.type) {
                    Type.Health.type -> list[0] = list[0] + 1
                    Type.Work.type -> list[1] = list[1] + 1
                    Type.MentalHealth.type -> list[2] = list[2] + 1
                    Type.Others.type -> list[3] = list[3] + 1
                }
            }
            Result.success(list)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun addNewTask(data: TaskUiData): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val id = db.addTask(
                Task(
                    id = data.id,
                    task = data.task,
                    type = data.type,
                    createTime = data.createTime,
                    startTime = data.addedTime,
                    alertTime = data.alertTime,
                    checked = data.checked,
                )
            )

            if (data.list.isNotEmpty()) {
                data.list.forEach {
                    dbItem.addTaskItem(
                        TaskItem(
                            id = it.id,
                            task = it.task,
                            createTime = it.createTime,
                            check = it.check,
                            taskId = id.toInt()
                        )
                    )
                }
            }
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun editTask(data: Task): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            db.updateTask(data)
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(data: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            db.deleteTaskById(data)
            db.deleteAllItemById(data)
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun editRoot(data: TaskUiData): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            db.updateTask(
                Task(
                    id = data.id,
                    type = data.type,
                    createTime = data.createTime,
                    alertTime = data.alertTime,
                    startTime = data.addedTime,
                    task = data.task,
                    checked = data.checked
                )
            )

            data.list.forEach {
                dbItem.updateTaskItem(it)
            }
            Result.success(Unit)

        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun editTaskItem(data: TaskItem): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            dbItem.updateTaskItem(data)
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun loadItem(data: Int): Result<TaskUiData> = withContext(Dispatchers.IO) {
        try {
            val request = db.getTaskById(data)
            val list = db.getAllItemById(request.id)
            val result = TaskUiData(
                id = request.id,
                task = request.task,
                type = request.type,
                createTime = request.createTime,
                alertTime = request.alertTime,
                addedTime = request.startTime,
                checked = request.checked,
                list = list
            )
            Result.success(result)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTaskItem(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            dbItem.removeTaskItem(id)
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}