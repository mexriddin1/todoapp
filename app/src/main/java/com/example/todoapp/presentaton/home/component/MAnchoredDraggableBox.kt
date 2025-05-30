package com.example.todoapp.presentaton.home.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.todoapp.core.ui.theme.DeleteBg
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MAnchoredDraggableBox(
    modifier: Modifier,
    firstContent: @Composable (modifier: Modifier) -> Unit,
    secondContent: @Composable (modifier: Modifier) -> Unit,
    offsetSize: Dp,
) {
    val density = LocalDensity.current
    val positionalThresholds: (totalDistance: Float) -> Float =
        { totalDistance -> totalDistance * 0.5f }
    val velocityThreshold: () -> Float = { with(density) { 100.dp.toPx() } }


    val state = remember(offsetSize) {
        AnchoredDraggableState(
            initialValue = DragAnchors.Start,
            positionalThresholds,
            velocityThreshold,
            animationSpec = tween()
        ).apply {
            val newAnchors = with(density) {
                DraggableAnchors {
                    DragAnchors.Start at 0.dp.toPx()
                    DragAnchors.End at -offsetSize.toPx()
                }
            }
            updateAnchors(newAnchors)
        }
    }

    Box(
        modifier = modifier
    ) {
        firstContent(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .offset {
                    IntOffset(
                        state
                            .requireOffset()
                            .roundToInt(), 0
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal)
        )
        secondContent(
            Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .offset {
                    IntOffset(
                        (state.requireOffset() + offsetSize.toPx()).roundToInt(), 0
                    )
                }
                .anchoredDraggable(state, Orientation.Horizontal)
        )
    }
}


@Composable
fun ItemDelete(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    id: Int = -1
) {
    Box(
        modifier = modifier
            .background(DeleteBg)
            .clickable { onClick(id) }
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Delete",
            color = Color.White
        )
    }
}


