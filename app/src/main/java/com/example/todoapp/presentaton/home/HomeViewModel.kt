package com.example.todoapp.presentaton.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val direction: HomeContract.Direction,
    private val useCase: UseCase
) : ViewModel(), HomeContract.ViewModel {

    override var uiState = MutableStateFlow(HomeContract.UiState())

    private fun reduce(block: (HomeContract.UiState) -> HomeContract.UiState) {
        val old = uiState.value
        val new = block.invoke(old)
        uiState.value = new
    }

    override fun update() {
        onAction(HomeContract.Intent.GetAllItem)
        onAction(HomeContract.Intent.GetAllItemCount)
    }

    override fun onAction(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.GetAllItem -> {
                useCase.getAllTask().onEach {
                    it.onSuccess { list ->
                        Log.d("TTT", "onAction: ${list}")

                        reduce {
                            it.copy(dataList = list)
                        }
                    }

                    it.onFailure {
                        Log.d("TTT", "onAction: $it")
                    }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.DeleteTask -> {
                useCase.deleteTask(intent.data).onEach {
                    it.onSuccess { list ->
                        onAction(HomeContract.Intent.GetAllItem)
                        onAction(HomeContract.Intent.GetAllItemCount)
                    }

                    it.onFailure {
                        Log.d("TTT", "onAction: ${it}")
                    }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.GetAllItemCount -> {
                useCase.getTaskCount().onEach {
                    it.onSuccess { list ->
                        reduce {
                            it.copy(listCount = list)
                        }
                    }

                    it.onFailure {
                        Log.d("TTT", "onAction: ${it}")

                    }
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.OpenNewTask -> {
                viewModelScope.launch {
                    direction.openNewTask()
                }
            }

            is HomeContract.Intent.OpenCalendar -> {
                viewModelScope.launch {
                    direction.openCalendar()
                }
            }


            is HomeContract.Intent.UpdateTask -> {
                useCase.editTask(intent.data).onEach {
                    it.onSuccess {}
                    it.onFailure {}
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.UpdateTaskItem -> {
                useCase.editTaskItem(intent.data).onEach {
                    it.onSuccess {}
                    it.onFailure {}
                }.launchIn(viewModelScope)
            }

            is HomeContract.Intent.OpenEditTask -> {
                viewModelScope.launch {
                    direction.openEditTask(intent.data)
                }
            }
        }
    }
}
