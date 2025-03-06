package com.example.todoapp.presentaton.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.core.ui.theme.CheckBoxBg
import com.example.todoapp.core.ui.theme.ComponentRadius

@Composable
fun CustomCheckBox(check: Boolean, click: (Boolean) -> Unit) {
    var isChecked by remember { mutableStateOf(check) }

    LaunchedEffect(check) {
        isChecked = check
    }

    Box(
        modifier = Modifier
            .padding(end = 10.dp)
            .height(25.dp)
            .width(25.dp)
            .border(
                width = 2.dp,
                color = CheckBoxBg,
                shape = ComponentRadius.middle
            )
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() }
            ) {
                isChecked = !isChecked
                click.invoke(isChecked)
            }
            .clip(ComponentRadius.small)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isChecked) CheckBoxBg else Color.White)
        ) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}