package com.example.todoapp.presentaton.new_task

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.local.room.entity.TaskItem
import com.example.todoapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    private val direction: NewTaskContract.Direction, private val useCase: UseCase
) : ViewModel(), NewTaskContract.ViewModel {

    override var uiState = MutableStateFlow(NewTaskContract.UiState())

    private fun reduce(block: (NewTaskContract.UiState) -> NewTaskContract.UiState) {
        val old = uiState.value
        val new = block.invoke(old)
        uiState.value = new
    }

    override fun onAction(intent: NewTaskContract.Intent) {
        when (intent) {
            is NewTaskContract.Intent.Back -> {
                viewModelScope.launch {
                    direction.back()
                }
            }

            is NewTaskContract.Intent.LoadTask -> {
                useCase.loadItem(intent.id).onEach {
                    it.onSuccess { data ->
                        reduce {
                            it.copy(task = data, title = data.task)
                        }

                        reduce { copy ->
                            copy.copy(
                                list = data.list.map {
                                    Pair(it.check, it.task)
                                }.toMutableStateList()
                            )
                        }
                    }

                    it.onFailure {
                        Log.d("BBB", "onAction: $it")

                    }
                }.launchIn(viewModelScope)
            }

            is NewTaskContract.Intent.EditNewTask -> {
                val list = mutableListOf<TaskItem>()

                if (intent.data.list.isNotEmpty()) {
                    val newTasks = intent.data.list
                    var count = 0
                    val updatedList = mutableListOf<TaskItem>()

                    uiState.value.task!!.list.forEachIndexed { index, taskItem ->
                        if (index < newTasks.size) {
                            if (newTasks[index].task.isBlank()) {
                                useCase.deleteTaskItem(taskItem.id).onEach {

                                }.launchIn(viewModelScope)
                            } else {
                                updatedList.add(
                                    TaskItem(
                                        id = taskItem.id,
                                        task = newTasks[index].task,
                                        createTime = taskItem.createTime,
                                        taskId = taskItem.taskId,
                                        check = newTasks[index].check
                                    )
                                )

                            }
                            count = index
                        } else {
                            useCase.deleteTaskItem(taskItem.id).onEach {

                            }.launchIn(viewModelScope)
                        }
                    }

                    if (count < newTasks.size) {
                        newTasks.filter {
                            it.task.isNotBlank()
                        }.forEachIndexed { index, newTask ->
                            if (index > count || (uiState.value.task!!.list.isEmpty() && index == 0)) {
                                updatedList.add(
                                    TaskItem(
                                        id = (System.currentTimeMillis().toInt() + index),
                                        task = newTask.task,
                                        createTime = System.currentTimeMillis(),
                                        taskId = uiState.value.task!!.id,
                                        check = newTask.check
                                    )
                                )
                            }
                        }
                    }

                    list.clear()
                    list.addAll(updatedList)
                }


                reduce {
                    it.copy(
                        task = it.task?.copy(
                            task = intent.data.task, list = list, type = intent.data.type
                        )
                    )
                }
                useCase.editRoot(uiState.value.task!!).onEach {
                    it.onSuccess {
                        onAction(NewTaskContract.Intent.Back)
                    }
                    it.onFailure {
                        Log.d("TTT", "$it")
                    }
                }.launchIn(viewModelScope)
            }

            is NewTaskContract.Intent.AddNewTask -> {
                val data = intent.data.copy(list = intent.data.list.filter {
                    it.task.isNotBlank()
                })

                useCase.addNewTask(data).onEach {
                    it.onSuccess {
                        onAction(NewTaskContract.Intent.Back)
                    }
                    it.onFailure {
                        Log.d("TTT", "$it")
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}
