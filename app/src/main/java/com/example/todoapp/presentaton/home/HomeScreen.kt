package com.example.todoapp.presentaton.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.example.todoapp.R
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.ButtonBg
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.data.local.room.entity.Task
import com.example.todoapp.presentaton.component.TopBarTitle
import com.example.todoapp.presentaton.home.component.HomeItem
import com.example.todoapp.presentaton.home.component.ItemDelete
import com.example.todoapp.presentaton.home.component.ListItem
import com.example.todoapp.presentaton.home.component.MAnchoredDraggableBox

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val uiState = viewModel.uiState.collectAsState()

        val context = LocalContext.current
        val notificationPermissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Notification permission granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

        HomeScreenContent(
            uiState, viewModel::onAction
        )

        viewModel.update()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: State<HomeContract.UiState> = remember { mutableStateOf(HomeContract.UiState()) },
    onAction: (HomeContract.Intent) -> Unit = {}
) {

    var state by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            TopAppBar(
                title = {
                    TopBarTitle("Today", System.currentTimeMillis()) {
                        onAction.invoke(HomeContract.Intent.OpenCalendar)
                    }
                }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                ), modifier = Modifier.padding(bottom = 10.dp)
            )
        }, floatingActionButton = {
            if (state) {
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp)
                        .background(ButtonBg, shape = ComponentRadius.regular)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onAction.invoke(HomeContract.Intent.OpenNewTask)
                            }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(innerPadding)
                            .height(230.dp)

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            HomeItem(Type.Health, uiState.value.listCount[0])
                            Spacer(modifier = Modifier.width(10.dp))
                            HomeItem(Type.Work, uiState.value.listCount[1])
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            HomeItem(Type.MentalHealth, uiState.value.listCount[2])
                            Spacer(modifier = Modifier.width(10.dp))
                            HomeItem(Type.Others, uiState.value.listCount[3])
                        }
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                }

                items(uiState.value.dataList) { item ->
                    val deleteComponentWidthSize = 100.dp
                    MAnchoredDraggableBox(
                        modifier = Modifier.fillMaxWidth(),
                        firstContent = { modifier ->
                            ListItem(
                                task = Task(
                                    id = item.id,
                                    task = item.task,
                                    type = item.type,
                                    createTime = item.createTime,
                                    alertTime = item.alertTime,
                                    startTime = item.addedTime,
                                    checked = item.checked,
                                ),
                                list = item.list,
                                modifier = modifier,
                                click = {
                                    onAction.invoke(HomeContract.Intent.UpdateTask(it))
                                },
                                clickItem = {
                                    onAction.invoke(HomeContract.Intent.UpdateTaskItem(it))
                                },
                                clickRoot = {
                                    onAction.invoke(HomeContract.Intent.OpenEditTask(it))
                                }
                            )
                        },
                        secondContent = { modifier ->
                            ItemDelete(
                                modifier = modifier
                                    .height(101.dp)
                                    .width(deleteComponentWidthSize),
                                onClick = {
                                    onAction.invoke(HomeContract.Intent.DeleteTask(it))
                                },
                                id = item.id
                            )
                        },
                        offsetSize = deleteComponentWidthSize
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}