package com.example.todoapp.presentaton.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.core.Type
import com.example.todoapp.core.ui.theme.ComponentRadius
import com.example.todoapp.core.ui.theme.HealthBg
import com.example.todoapp.core.ui.theme.MentalHealthBg
import com.example.todoapp.core.ui.theme.OthersBg
import com.example.todoapp.core.ui.theme.OthersText
import com.example.todoapp.core.ui.theme.WorkBg

@Composable
fun RowScope.HomeItem(type: Type, count: Int) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(
                when (type) {
                    Type.Health -> HealthBg
                    Type.Work -> WorkBg
                    Type.MentalHealth -> MentalHealthBg
                    Type.Others -> OthersBg
                }, shape = ComponentRadius.regular
            )
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(
                when (type) {
                    Type.Health -> R.drawable.health
                    Type.Work -> R.drawable.work
                    Type.MentalHealth -> R.drawable.mental_health
                    Type.Others -> R.drawable.other
                }
            ), contentDescription = null, modifier = Modifier.size(22.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Row {
            Text(
                text = count.toString(), style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = W700,
                    fontFamily = FontFamily.SansSerif
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = type.type, style = TextStyle(
                    fontSize = 20.sp,
                    color = OthersText,
                    fontWeight = W500,
                    fontFamily = FontFamily.SansSerif
                )
            )
        }
    }
}