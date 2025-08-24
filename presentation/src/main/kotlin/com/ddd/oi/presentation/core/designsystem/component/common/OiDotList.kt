package com.ddd.oi.presentation.core.designsystem.component.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek

@Composable
fun OiDotList(
    modifier: Modifier = Modifier,
    dotList: List<List<Color>>
) {
    Row(
        modifier = modifier
            .height(6.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        DayOfWeek.entries.run {
            drop(DayOfWeek.SUNDAY.value.dec()) + take(DayOfWeek.SUNDAY.value.dec())
        }.forEachIndexed { index, _ ->
            LazyRow(
                modifier = Modifier.width(36.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
            ) {
                items(dotList[index]) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(it)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun OiDotListPreview() {
    OiDotList(
        dotList = listOf(
            listOf(Color.Red),
            listOf(Color.Red, Color.Blue),
            listOf(Color.Red, Color.Blue, Color.Green),
            listOf(Color.Red),
            listOf(Color.Red, Color.Blue),
            listOf(Color.Red, Color.Blue, Color.Green),
            listOf(Color.Red),
        )
    )
}